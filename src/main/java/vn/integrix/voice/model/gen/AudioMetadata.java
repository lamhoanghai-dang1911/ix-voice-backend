package vn.integrix.voice.model.gen;

import com.fasterxml.jackson.annotation.*;

public class AudioMetadata {
    private Long channels;
    private Long durationMS;
    private String filename;
    private long fileSize;
    private String format;
    private Long sampleRate;

    @JsonProperty("channels")
    public Long getChannels() { return channels; }
    @JsonProperty("channels")
    public void setChannels(Long value) { this.channels = value; }

    @JsonProperty("durationMs")
    public Long getDurationMS() { return durationMS; }
    @JsonProperty("durationMs")
    public void setDurationMS(Long value) { this.durationMS = value; }

    @JsonProperty("filename")
    public String getFilename() { return filename; }
    @JsonProperty("filename")
    public void setFilename(String value) { this.filename = value; }

    @JsonProperty("fileSize")
    public long getFileSize() { return fileSize; }
    @JsonProperty("fileSize")
    public void setFileSize(long value) { this.fileSize = value; }

    @JsonProperty("format")
    public String getFormat() { return format; }
    @JsonProperty("format")
    public void setFormat(String value) { this.format = value; }

    @JsonProperty("sampleRate")
    public Long getSampleRate() { return sampleRate; }
    @JsonProperty("sampleRate")
    public void setSampleRate(Long value) { this.sampleRate = value; }
}
