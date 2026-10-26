package vn.integrix.voice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("INTEGRIX IX.VOICE API Gateway")
                        .version("0.1.0")
                        .description("Speech & Conversation Intelligence Platform API Gateway (Spring Boot 3 + Java 21 Virtual Threads). " +
                                "Single Source of Truth: contracts/schemas/audio_job_v0_1.json & transcript_v0_1.json.")
                        .contact(new Contact()
                                .name("INTEGRIX Architecture Team")
                                .email("engineering@integrix.vn"))
                        .license(new License().name("Internal Proprietary")));
    }
}
