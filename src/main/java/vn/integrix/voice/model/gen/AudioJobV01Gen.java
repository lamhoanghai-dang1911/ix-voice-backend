package vn.integrix.voice.model.gen;

import com.fasterxml.jackson.annotation.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Canonical contract for audio processing job state in IX.VOICE v0.1
 */
public class AudioJobV01Gen {
    private AudioMetadata audioMetadata;
    private OffsetDateTime createdAt;
    private List<Error> errors;
    private UUID jobID;
    private long progress;
    private Status status;
    private String traceID;
    private OffsetDateTime updatedAt;

    @JsonProperty("audioMetadata")
    public AudioMetadata getAudioMetadata() { return audioMetadata; }
    @JsonProperty("audioMetadata")
    public void setAudioMetadata(AudioMetadata value) { this.audioMetadata = value; }

    @JsonProperty("createdAt")
    public OffsetDateTime getCreatedAt() { return createdAt; }
    @JsonProperty("createdAt")
    public void setCreatedAt(OffsetDateTime value) { this.createdAt = value; }

    @JsonProperty("errors")
    public List<Error> getErrors() { return errors; }
    @JsonProperty("errors")
    public void setErrors(List<Error> value) { this.errors = value; }

    @JsonProperty("jobId")
    public UUID getJobID() { return jobID; }
    @JsonProperty("jobId")
    public void setJobID(UUID value) { this.jobID = value; }

    @JsonProperty("progress")
    public long getProgress() { return progress; }
    @JsonProperty("progress")
    public void setProgress(long value) { this.progress = value; }

    @JsonProperty("status")
    public Status getStatus() { return status; }
    @JsonProperty("status")
    public void setStatus(Status value) { this.status = value; }

    @JsonProperty("traceId")
    public String getTraceID() { return traceID; }
    @JsonProperty("traceId")
    public void setTraceID(String value) { this.traceID = value; }

    @JsonProperty("updatedAt")
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    @JsonProperty("updatedAt")
    public void setUpdatedAt(OffsetDateTime value) { this.updatedAt = value; }
}
