package be.ocrapi.model;

import lombok.Data;

@Data

public class OcrResult {
    private Double MeanConfidenceLevel;
    private String TextResult;
}
