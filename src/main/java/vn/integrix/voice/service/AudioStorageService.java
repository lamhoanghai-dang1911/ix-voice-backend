package vn.integrix.voice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.integrix.voice.model.AudioJobDTO;
import vn.integrix.voice.model.TranscriptDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class AudioStorageService {

    private final Path storageDir;
    private final Path fixturesDir;
    private final ObjectMapper objectMapper;

    public AudioStorageService(
            @Value("${app.storage.dir:./data/storage}") String storageDirPath,
            @Value("${app.storage.fixtures-dir:./contracts/fixtures}") String fixturesDirPath,
            ObjectMapper objectMapper) {
        this.storageDir = Paths.get(storageDirPath).toAbsolutePath().normalize();
        this.fixturesDir = Paths.get(fixturesDirPath).toAbsolutePath().normalize();
        this.objectMapper = objectMapper;

        try {
            Files.createDirectories(this.storageDir);
            log.info("Initialized AudioStorageService with storageDir: {}", this.storageDir);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage directory", e);
        }
    }

    public Path saveAudioFile(String jobId, MultipartFile file) throws IOException {
        Path jobDir = storageDir.resolve(jobId);
        Files.createDirectories(jobDir);

        String originalFilename = file.getOriginalFilename();
        String safeName = (originalFilename != null && !originalFilename.isBlank())
                ? originalFilename.replaceAll("[^a-zA-Z0-9._-]", "_")
                : "audio.wav";

        Path destination = jobDir.resolve(safeName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        log.info("Saved audio file to {}", destination);
        return destination;
    }

    public void saveJob(String jobId, AudioJobDTO job) {
        try {
            Path jobDir = storageDir.resolve(jobId);
            Files.createDirectories(jobDir);
            Path jobFile = jobDir.resolve("job.json");
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jobFile.toFile(), job);
        } catch (IOException e) {
            log.error("Failed to persist job.json for jobId {}: {}", jobId, e.getMessage());
        }
    }

    public Optional<AudioJobDTO> getJob(String jobId) {
        Path jobFile = storageDir.resolve(jobId).resolve("job.json");
        if (Files.exists(jobFile)) {
            try {
                return Optional.of(objectMapper.readValue(jobFile.toFile(), AudioJobDTO.class));
            } catch (IOException e) {
                log.error("Error reading job.json for jobId {}: {}", jobId, e.getMessage());
            }
        }
        return Optional.empty();
    }

    public void saveTranscript(String jobId, TranscriptDTO transcript) {
        try {
            Path jobDir = storageDir.resolve(jobId);
            Files.createDirectories(jobDir);
            Path transcriptFile = jobDir.resolve("transcript.json");
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(transcriptFile.toFile(), transcript);
            log.info("Saved transcript.json for jobId {}", jobId);
        } catch (IOException e) {
            log.error("Failed to persist transcript.json for jobId {}: {}", jobId, e.getMessage());
        }
    }

    public Optional<TranscriptDTO> getTranscript(String jobId) {
        Path transcriptFile = storageDir.resolve(jobId).resolve("transcript.json");
        if (Files.exists(transcriptFile)) {
            try {
                return Optional.of(objectMapper.readValue(transcriptFile.toFile(), TranscriptDTO.class));
            } catch (IOException e) {
                log.error("Error reading transcript.json for jobId {}: {}", jobId, e.getMessage());
            }
        }
        return Optional.empty();
    }

    public Map<String, Object> loadFixture(String fixtureFilename) {
        Path fixturePath = fixturesDir.resolve(fixtureFilename);
        if (!Files.exists(fixturePath)) {
            // Docker fallback
            Path altPath = Paths.get("/app/contracts/fixtures").resolve(fixtureFilename);
            if (Files.exists(altPath)) {
                fixturePath = altPath;
            }
        }

        if (Files.exists(fixturePath)) {
            try {
                return objectMapper.readValue(fixturePath.toFile(), Map.class);
            } catch (IOException e) {
                log.warn("Failed to parse fixture {}: {}", fixtureFilename, e.getMessage());
            }
        }
        return Map.of();
    }

    public Path getStorageDir() {
        return storageDir;
    }
}
