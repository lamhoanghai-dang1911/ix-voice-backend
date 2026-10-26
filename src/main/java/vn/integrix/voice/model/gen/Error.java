package vn.integrix.voice.model.gen;

import com.fasterxml.jackson.annotation.*;
import java.time.OffsetDateTime;

public class Error {
    private String code;
    private String message;
    private String stage;
    private OffsetDateTime timestamp;

    @JsonProperty("code")
    public String getCode() { return code; }
    @JsonProperty("code")
    public void setCode(String value) { this.code = value; }

    @JsonProperty("message")
    public String getMessage() { return message; }
    @JsonProperty("message")
    public void setMessage(String value) { this.message = value; }

    @JsonProperty("stage")
    public String getStage() { return stage; }
    @JsonProperty("stage")
    public void setStage(String value) { this.stage = value; }

    @JsonProperty("timestamp")
    public OffsetDateTime getTimestamp() { return timestamp; }
    @JsonProperty("timestamp")
    public void setTimestamp(OffsetDateTime value) { this.timestamp = value; }
}
