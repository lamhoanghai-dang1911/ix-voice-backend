package vn.integrix.voice.model.gen;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum Status {
    ASR_DIARIZATION, COMPLETED, FAILED, NLP_ANALYSIS, QUEUED, VAD_PROCESSING, VALIDATING;

    @JsonValue
    public String toValue() {
        switch (this) {
            case ASR_DIARIZATION: return "ASR_DIARIZATION";
            case COMPLETED: return "COMPLETED";
            case FAILED: return "FAILED";
            case NLP_ANALYSIS: return "NLP_ANALYSIS";
            case QUEUED: return "QUEUED";
            case VAD_PROCESSING: return "VAD_PROCESSING";
            case VALIDATING: return "VALIDATING";
        }
        return null;
    }

    @JsonCreator
    public static Status forValue(String value) throws IOException {
        if (value.equals("ASR_DIARIZATION")) return ASR_DIARIZATION;
        if (value.equals("COMPLETED")) return COMPLETED;
        if (value.equals("FAILED")) return FAILED;
        if (value.equals("NLP_ANALYSIS")) return NLP_ANALYSIS;
        if (value.equals("QUEUED")) return QUEUED;
        if (value.equals("VAD_PROCESSING")) return VAD_PROCESSING;
        if (value.equals("VALIDATING")) return VALIDATING;
        throw new IOException("Cannot deserialize Status");
    }
}
