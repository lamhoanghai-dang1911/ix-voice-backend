package vn.integrix.voice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VoiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(VoiceApplication.class, args);
    }
}
