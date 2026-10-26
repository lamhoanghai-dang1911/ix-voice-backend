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
public class TranscriptSegmentDTO {
    private String id;
    private Long startMs;
    private Long endMs;
    private String speakerId;
    private String speakerLabel;
    private String text;
    private Double confidence;
    private List<WordSegmentDTO> words;
}
