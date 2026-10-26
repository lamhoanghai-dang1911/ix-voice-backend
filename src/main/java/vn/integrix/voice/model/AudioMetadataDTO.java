package vn.integrix.voice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AudioMetadataDTO {
    private String filename;
    private Long fileSize;
    private String format;
    private Long durationMs;
    private Integer sampleRate;
    private Integer channels;
}
