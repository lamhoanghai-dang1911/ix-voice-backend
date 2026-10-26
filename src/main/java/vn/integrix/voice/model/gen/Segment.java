package vn.integrix.voice.model.gen;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class Segment {
    private double confidence;
    private long endMS;
    private String id;
    private String speakerID;
    private String speakerLabel;
    private long startMS;
    private String text;
    private List<Word> words;

    @JsonProperty("confidence")
    public double getConfidence() { return confidence; }
    @JsonProperty("confidence")
    public void setConfidence(double value) { this.confidence = value; }

    @JsonProperty("endMs")
    public long getEndMS() { return endMS; }
    @JsonProperty("endMs")
    public void setEndMS(long value) { this.endMS = value; }

    @JsonProperty("id")
    public String getID() { return id; }
    @JsonProperty("id")
    public void setID(String value) { this.id = value; }

    @JsonProperty("speakerId")
    public String getSpeakerID() { return speakerID; }
    @JsonProperty("speakerId")
    public void setSpeakerID(String value) { this.speakerID = value; }

    @JsonProperty("speakerLabel")
    public String getSpeakerLabel() { return speakerLabel; }
    @JsonProperty("speakerLabel")
    public void setSpeakerLabel(String value) { this.speakerLabel = value; }

    @JsonProperty("startMs")
    public long getStartMS() { return startMS; }
    @JsonProperty("startMs")
    public void setStartMS(long value) { this.startMS = value; }

    @JsonProperty("text")
    public String getText() { return text; }
    @JsonProperty("text")
    public void setText(String value) { this.text = value; }

    @JsonProperty("words")
    public List<Word> getWords() { return words; }
    @JsonProperty("words")
    public void setWords(List<Word> value) { this.words = value; }
}
