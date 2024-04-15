package be.ocrapi.service;

import be.ocrapi.model.OcrResult;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class OcrService {
    @Autowired
    private Tesseract tesseract;

    public OcrResult ocr(MultipartFile file) throws IOException, TesseractException {
        tesseract.setLanguage("eng");
//        tesseract.setLanguage("eng");
//        tesseract.setPageSegMode(1);
//        tesseract.setOcrEngineMode(1);
        File convFile = convert(file);
        String text = tesseract.doOCR(convFile);
        OcrResult ocrResult = new OcrResult();
        System.out.println(ocrResult);
//        ocrResult.setResult(text);
        ocrResult.setResult(text.split("\n"));
        System.out.print(ocrResult);
        return ocrResult;
    }

    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File("uploads/" + file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public String recognizeText(InputStream inputStream) throws IOException {
        BufferedImage image = ImageIO.read(inputStream);
        try {
//            String text =  tesseract.doOCR(image);
            return tesseract.doOCR(image);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return "failed";
    }
}
