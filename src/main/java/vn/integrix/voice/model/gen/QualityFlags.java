package vn.integrix.voice.model.gen;

import com.fasterxml.jackson.annotation.*;

public class QualityFlags {
    private Double averageConfidence;
    private Long lowConfidenceSegmentsCount;
    private Double snrEstimateDB;

    @JsonProperty("averageConfidence")
    public Double getAverageConfidence() { return averageConfidence; }
    @JsonProperty("averageConfidence")
    public void setAverageConfidence(Double value) { this.averageConfidence = value; }

    @JsonProperty("lowConfidenceSegmentsCount")
    public Long getLowConfidenceSegmentsCount() { return lowConfidenceSegmentsCount; }
    @JsonProperty("lowConfidenceSegmentsCount")
    public void setLowConfidenceSegmentsCount(Long value) { this.lowConfidenceSegmentsCount = value; }

    @JsonProperty("snrEstimateDb")
    public Double getSnrEstimateDB() { return snrEstimateDB; }
    @JsonProperty("snrEstimateDb")
    public void setSnrEstimateDB(Double value) { this.snrEstimateDB = value; }
}
