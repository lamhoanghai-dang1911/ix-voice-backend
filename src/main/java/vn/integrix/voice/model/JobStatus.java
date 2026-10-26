package vn.integrix.voice.model;

public enum JobStatus {
    QUEUED,
    VALIDATING,
    VAD_PROCESSING,
    ASR_DIARIZATION,
    NLP_ANALYSIS,
    COMPLETED,
    FAILED
}
