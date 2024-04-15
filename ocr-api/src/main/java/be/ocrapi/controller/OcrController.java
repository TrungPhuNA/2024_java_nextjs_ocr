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
            var ocr = ocrService.ocr(file);
            log.debug("[OCR CONTROLLER]------>error create", ocr.getResult());
            return BaseResponse.ofSucceeded(ocr.getResult());
        } catch (Exception e) {
            log.debug("[OCR CONTROLLER]------>error create", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[OCR CONTROLLER]------>create", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @PostMapping("/upload/v2")
    public String[] recognizeText(@RequestParam("file") MultipartFile file) throws IOException {
        var response = ocrService.recognizeText(file.getInputStream());
        var data = response.split("\n");
        for(var i = 1 ; i < data.length ; i ++) {
            var item = data[i].split(" ");
            System.out.print("===== CHEKC LENGHT  =====" + item.length);
            if (item.length == 5) {
                System.out.print("===== SẢN PHẨM =====");
                System.out.print("===== SẢN PHẨM =====" +  item[0]);
            }
        }
        return data;
    }
}
