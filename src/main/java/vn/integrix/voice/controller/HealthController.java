package vn.integrix.voice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/health")
@Tag(name = "Health", description = "Service health check and runtime capabilities")
public class HealthController {

    @Value("${app.name:INTEGRIX IX.VOICE}")
    private String appName;

    @Value("${app.version:0.1.0}")
    private String appVersion;

    @GetMapping
    @Operation(summary = "Check backend service health and Virtual Threads runtime")
    public ResponseEntity<Map<String, Object>> getHealth() {
        boolean isVirtualThread = Thread.currentThread().isVirtual();
        return ResponseEntity.ok(Map.of(
                "status", "healthy",
                "service", appName,
                "version", appVersion,
                "javaVersion", System.getProperty("java.version"),
                "virtualThreadsEnabled", isVirtualThread,
                "timestamp", System.currentTimeMillis()
        ));
    }
}
