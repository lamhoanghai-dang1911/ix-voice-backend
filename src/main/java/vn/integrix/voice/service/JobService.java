package vn.integrix.voice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.integrix.voice.model.*;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class JobService {

    private final AudioStorageService storageService;
    private final AiServiceClient aiServiceClient;
    private final Map<String, AudioJobDTO> jobCache = new ConcurrentHashMap<>();

    public JobService(AudioStorageService storageService, AiServiceClient aiServiceClient) {
        this.storageService = storageService;
        this.aiServiceClient = aiServiceClient;
    }

    public AudioJobDTO createJob(MultipartFile file) throws IOException {
        String jobId = UUID.randomUUID().toString();
        String traceId = "trace-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        String nowIso = Instant.now().toString();

        // 1. Save uploaded file to shared storage
        Path savedAudioPath = storageService.saveAudioFile(jobId, file);

        // 2. Initialize job state
        AudioJobDTO job = AudioJobDTO.builder()
                .jobId(jobId)
                .status(JobStatus.QUEUED)
                .progress(0)
                .audioMetadata(AudioMetadataDTO.builder()
                        .filename(file.getOriginalFilename() != null ? file.getOriginalFilename() : "audio.wav")
                        .fileSize(file.getSize())
                        .format(file.getContentType() != null ? file.getContentType() : "audio/wav")
                        .channels(1)
                        .sampleRate(16000)
                        .build())
                .traceId(traceId)
                .errors(new ArrayList<>())
                .createdAt(nowIso)
                .updatedAt(nowIso)
                .build();

        jobCache.put(jobId, job);
        storageService.saveJob(jobId, job);

        log.info("Created job {} for file {}", jobId, file.getOriginalFilename());

        // 3. Trigger asynchronous pipeline execution on Virtual Thread
        processJobAsync(jobId, savedAudioPath.toString());

        return job;
    }

    @Async
    public void processJobAsync(String jobId, String audioPath) {
        log.info("[VirtualThread: {}] Starting async pipeline for jobId: {}",
                Thread.currentThread().isVirtual(), jobId);

        try {
            // Stage 1: Validating
            updateJobStage(jobId, JobStatus.VALIDATING, 15);
            Thread.sleep(400); // Simulated audio duration/VAD check

            // Stage 2: ASR & Diarization (Calling AI Service)
            updateJobStage(jobId, JobStatus.ASR_DIARIZATION, 40);

            TranscriptDTO transcript = aiServiceClient.requestTranscription(jobId, audioPath);
            storageService.saveTranscript(jobId, transcript);

            // Stage 3: Completed
            updateJobStage(jobId, JobStatus.COMPLETED, 100);
            log.info("Successfully finished processing jobId: {}", jobId);

        } catch (Exception e) {
            log.error("Job {} failed during pipeline processing: {}", jobId, e.getMessage(), e);
            failJob(jobId, "PIPELINE_ERROR", e.getMessage(), "ASR_DIARIZATION");
        }
    }

    private void updateJobStage(String jobId, JobStatus status, int progress) {
        AudioJobDTO job = jobCache.computeIfPresent(jobId, (k, current) -> {
            current.setStatus(status);
            current.setProgress(progress);
            current.setUpdatedAt(Instant.now().toString());
            return current;
        });

        if (job != null) {
            storageService.saveJob(jobId, job);
        }
    }

    private void failJob(String jobId, String code, String message, String stage) {
        AudioJobDTO job = jobCache.computeIfPresent(jobId, (k, current) -> {
            current.setStatus(JobStatus.FAILED);
            current.setUpdatedAt(Instant.now().toString());
            current.getErrors().add(JobErrorDTO.builder()
                    .code(code)
                    .message(message)
                    .stage(stage)
                    .timestamp(Instant.now().toString())
                    .build());
            return current;
        });

        if (job != null) {
            storageService.saveJob(jobId, job);
        }
    }

    public Optional<AudioJobDTO> getJob(String jobId) {
        if (jobCache.containsKey(jobId)) {
            return Optional.of(jobCache.get(jobId));
        }
        Optional<AudioJobDTO> fromFile = storageService.getJob(jobId);
        fromFile.ifPresent(j -> jobCache.put(jobId, j));
        return fromFile;
    }

    public Optional<TranscriptDTO> getTranscript(String jobId) {
        return storageService.getTranscript(jobId);
    }
}
