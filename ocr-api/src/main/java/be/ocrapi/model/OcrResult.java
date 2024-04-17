package be.ocrapi.model;

import lombok.Data;

@Data

public class OcrResult {
//    private Double MeanConfidenceLevel;
//    private String TextResult;
private String[] result;
      private String  fileName;
}
