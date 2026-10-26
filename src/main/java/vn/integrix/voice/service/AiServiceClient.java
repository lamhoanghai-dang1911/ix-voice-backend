package vn.integrix.voice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import vn.integrix.voice.model.TranscribeRequestDTO;
import vn.integrix.voice.model.TranscriptDTO;

import java.time.Duration;

@Slf4j
@Service
public class AiServiceClient {

    private final RestClient restClient;
    private final String aiServiceUrl;

    public AiServiceClient(
            @Value("${app.ai-service.url:http://localhost:5000}") String aiServiceUrl,
            @Value("${app.ai-service.connect-timeout-ms:5000}") int connectTimeoutMs,
            @Value("${app.ai-service.read-timeout-ms:120000}") int readTimeoutMs) {
        this.aiServiceUrl = aiServiceUrl;

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofMillis(connectTimeoutMs));
        requestFactory.setReadTimeout(Duration.ofMillis(readTimeoutMs));

        this.restClient = RestClient.builder()
                .baseUrl(aiServiceUrl)
                .requestFactory(requestFactory)
                .build();

        log.info("Initialized AiServiceClient with AI Service URL: {}", aiServiceUrl);
    }

    public TranscriptDTO requestTranscription(String jobId, String audioPath) {
        log.info("Requesting transcription from AI service at {} for jobId={}, audioPath={}",
                aiServiceUrl, jobId, audioPath);

        TranscribeRequestDTO request = TranscribeRequestDTO.builder()
                .jobId(jobId)
                .audioPath(audioPath)
                .build();

        try {
            return restClient.post()
                    .uri("/api/v1/ai/transcribe")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(TranscriptDTO.class);
        } catch (Exception e) {
            log.error("Failed to call AI Service at {}/api/v1/ai/transcribe: {}", aiServiceUrl, e.getMessage());
            throw new RuntimeException("AI Service transcription failed: " + e.getMessage(), e);
        }
    }
}
