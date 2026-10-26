package vn.integrix.voice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TranscriptDTO {
    private String jobId;
    private String modelVersion;
    @Builder.Default
    private String language = "vi";
    private QualityFlagsDTO qualityFlags;
    private List<TranscriptSegmentDTO> segments;
}
