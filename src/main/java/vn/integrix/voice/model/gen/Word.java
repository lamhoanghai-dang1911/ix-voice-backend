package vn.integrix.voice.model.gen;

import com.fasterxml.jackson.annotation.*;

public class Word {
    private Double confidence;
    private long endMS;
    private long startMS;
    private String word;

    @JsonProperty("confidence")
    public Double getConfidence() { return confidence; }
    @JsonProperty("confidence")
    public void setConfidence(Double value) { this.confidence = value; }

    @JsonProperty("endMs")
    public long getEndMS() { return endMS; }
    @JsonProperty("endMs")
    public void setEndMS(long value) { this.endMS = value; }

    @JsonProperty("startMs")
    public long getStartMS() { return startMS; }
    @JsonProperty("startMs")
    public void setStartMS(long value) { this.startMS = value; }

    @JsonProperty("word")
    public String getWord() { return word; }
    @JsonProperty("word")
    public void setWord(String value) { this.word = value; }
}
