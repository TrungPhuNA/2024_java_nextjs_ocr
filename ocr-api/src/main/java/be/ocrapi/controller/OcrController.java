package be.ocrapi.controller;

import be.ocrapi.common.BaseResponse;
import be.ocrapi.common.BusinessErrorCode;
import be.ocrapi.common.BusinessException;
import be.ocrapi.response.FileResponse;
import be.ocrapi.service.OcrService;
import be.ocrapi.service.Statistic.StatisticService;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class OcrController {
    @Autowired
    private OcrService ocrService;

    @Autowired
    private StatisticService statisticService;

    @GetMapping("statistic/list")
    public BaseResponse<?> statistic(@RequestParam(name = "month", required = false) String month) {
        try {
            var data = statisticService.getStatistic(month);
            return BaseResponse.ofSucceeded().setData(data);
        } catch (Exception e) {
            String message = e.getMessage();
            log.error("[OCR CONTROLLER]------>create" + message);
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[OCR CONTROLLER]------>create" + message);
            return BaseResponse.ofFailed(error);
        }
    }

    @PostMapping("upload/file")
    public BaseResponse<?> upload(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            var ocr = ocrService.convert(file);
//            FileResponse fileData = new FileResponse(ocr);
            return BaseResponse.ofSucceeded().setData(ocr.getName());
        } catch (Exception e) {
            String message = e.getMessage();
            log.error("[OCR CONTROLLER]------>create" + message);
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[OCR CONTROLLER]------>create" + message);
            return BaseResponse.ofFailed(error);
        }
    }

    @PostMapping("ocr/upload")
    public BaseResponse<?> uploadOcr(@RequestParam("file") MultipartFile file) throws IOException, TesseractException {
        try {
            var ocr = ocrService.ocr(file);
            return BaseResponse.ofSucceeded(ocr);
        } catch (Exception e) {
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[OCR CONTROLLER]------>create" + message);
            return BaseResponse.ofFailed(error);
        }
    }

    @GetMapping("/files/{name}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String name) {
        // Load file as Resource
        Path filePath = Paths.get("uploads/").resolve(name).normalize();
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                // Determine content type dynamically
                String contentType = "image/jpeg"; // Modify according to your image type
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException ex) {
            return ResponseEntity.notFound().build();
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
