package be.ocrapi.controller;

import be.ocrapi.common.BaseResponse;
import be.ocrapi.common.BusinessErrorCode;
import be.ocrapi.common.BusinessException;
import be.ocrapi.service.OcrService;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/ocr")
@Slf4j
public class OcrController {
    @Autowired
    private OcrService ocrService;

    @PostMapping("upload")
    public BaseResponse<?> upload(@RequestParam("file") MultipartFile file) throws IOException, TesseractException {
        try {
            var ocr = ocrService.ocrV2(file);
            return BaseResponse.ofSucceeded(ocr);
        } catch (Exception e) {
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[OCR CONTROLLER]------>create" + message);
            return BaseResponse.ofFailed(error);
        }
    }
}
