package be.ocrapi.controller;

import be.ocrapi.model.OcrResult;
import be.ocrapi.service.OcrService;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/ocr")
@Slf4j
public class OcrController {
    @Autowired
    private OcrService ocrService;

    @PostMapping("/upload")
    public ResponseEntity<OcrResult> upload(@RequestParam("file") MultipartFile file) throws IOException, TesseractException {
        return ResponseEntity.ok(ocrService.ocr(file));
    }
}
