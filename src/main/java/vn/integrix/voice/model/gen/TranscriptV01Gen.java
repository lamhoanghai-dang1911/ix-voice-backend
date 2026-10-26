package vn.integrix.voice.model.gen;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import java.util.UUID;

/**
 * Canonical contract for timestamped and speaker-attributed Vietnamese transcripts
 */
public class TranscriptV01Gen {
    private UUID jobID;
    private String language;
    private String modelVersion;
    private QualityFlags qualityFlags;
    private List<Segment> segments;

    @JsonProperty("jobId")
    public UUID getJobID() { return jobID; }
    @JsonProperty("jobId")
    public void setJobID(UUID value) { this.jobID = value; }

    @JsonProperty("language")
    public String getLanguage() { return language; }
    @JsonProperty("language")
    public void setLanguage(String value) { this.language = value; }

    @JsonProperty("modelVersion")
    public String getModelVersion() { return modelVersion; }
    @JsonProperty("modelVersion")
    public void setModelVersion(String value) { this.modelVersion = value; }

    @JsonProperty("qualityFlags")
    public QualityFlags getQualityFlags() { return qualityFlags; }
    @JsonProperty("qualityFlags")
    public void setQualityFlags(QualityFlags value) { this.qualityFlags = value; }

    @JsonProperty("segments")
    public List<Segment> getSegments() { return segments; }
    @JsonProperty("segments")
    public void setSegments(List<Segment> value) { this.segments = value; }
}
