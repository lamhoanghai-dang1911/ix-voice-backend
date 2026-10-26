package vn.integrix.voice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AudioJobDTO {
    private String jobId;
    private JobStatus status;
    private Integer progress;
    private AudioMetadataDTO audioMetadata;
    private String traceId;
    @Builder.Default
    private List<JobErrorDTO> errors = new ArrayList<>();
    private String createdAt;
    private String updatedAt;
}
