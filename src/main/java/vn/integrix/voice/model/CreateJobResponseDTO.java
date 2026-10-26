package vn.integrix.voice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobResponseDTO {
    private String jobId;
    private JobStatus status;
    private String traceId;
}
