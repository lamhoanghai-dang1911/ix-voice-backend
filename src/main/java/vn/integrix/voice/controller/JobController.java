package vn.integrix.voice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.integrix.voice.model.AudioJobDTO;
import vn.integrix.voice.model.CreateJobResponseDTO;
import vn.integrix.voice.model.TranscriptDTO;
import vn.integrix.voice.service.AudioStorageService;
import vn.integrix.voice.service.JobService;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/jobs")
@Tag(name = "Audio Jobs", description = "Endpoints for managing Audio Processing Jobs & Transcripts")
public class JobController {

    private final JobService jobService;
    private final AudioStorageService storageService;

    public JobController(JobService jobService, AudioStorageService storageService) {
        this.jobService = jobService;
        this.storageService = storageService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload audio and create processing job",
            description = "Receives audio file, saves to shared storage, and dispatches processing pipeline asynchronously.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job created successfully",
                    content = @Content(schema = @Schema(implementation = CreateJobResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid file or empty payload")
    })
    public ResponseEntity<CreateJobResponseDTO> createJob(
            @Parameter(description = "Audio file (WAV, MP3, M4A, etc.)", required = true)
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            AudioJobDTO job = jobService.createJob(file);
            CreateJobResponseDTO response = CreateJobResponseDTO.builder()
                    .jobId(job.getJobId())
                    .status(job.getStatus())
                    .traceId(job.getTraceId())
                    .build();
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            log.error("Failed to process file upload: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{jobId}")
    @Operation(summary = "Get audio job status and metadata",
            description = "Returns current progress, status and metadata conforming to AudioJobV01 contract.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job details found",
                    content = @Content(schema = @Schema(implementation = AudioJobDTO.class))),
            @ApiResponse(responseCode = "404", description = "Job not found")
    })
    public ResponseEntity<AudioJobDTO> getJob(@PathVariable String jobId) {
        return jobService.getJob(jobId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{jobId}/transcript")
    @Operation(summary = "Get transcription and speaker diarization results",
            description = "Returns timestamps and segments conforming to TranscriptV01 contract.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transcript found",
                    content = @Content(schema = @Schema(implementation = TranscriptDTO.class))),
            @ApiResponse(responseCode = "404", description = "Transcript not ready or job not found")
    })
    public ResponseEntity<TranscriptDTO> getTranscript(@PathVariable String jobId) {
        return jobService.getTranscript(jobId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{jobId}/analysis")
    @Operation(summary = "Get NLP summary, Prudential checklist matching, and emotion signals")
    public ResponseEntity<Map<String, Object>> getAnalysis(@PathVariable String jobId) {
        Map<String, Object> fixture = storageService.loadFixture("mock_analysis_emotion.json");
        return ResponseEntity.ok(fixture);
    }

    @GetMapping("/{jobId}/qa-review")
    @Operation(summary = "Get evidence and QA review checklist status")
    public ResponseEntity<Map<String, Object>> getQAReview(@PathVariable String jobId) {
        Map<String, Object> fixture = storageService.loadFixture("mock_evidence_qa.json");
        return ResponseEntity.ok(fixture);
    }

    @PostMapping("/{jobId}/qa-review")
    @Operation(summary = "Submit manual review decision for compliance QA audit")
    public ResponseEntity<Map<String, Object>> submitQAReview(
            @PathVariable String jobId,
            @RequestBody Map<String, Object> payload) {
        log.info("Received QA review submission for jobId {}: {}", jobId, payload);
        return ResponseEntity.ok(payload);
    }
}
