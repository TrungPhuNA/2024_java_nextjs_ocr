package be.ocrapi.service;

import be.ocrapi.common.BaseResponse;
import be.ocrapi.model.OcrResult;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
public class OcrService {
    @Autowired
    private Tesseract tesseract;
    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

    public OcrResult ocr(MultipartFile file) throws IOException, TesseractException {

        try {
            Path staticPath = Paths.get("static");
            Path imagePath = Paths.get("images");

            if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
                Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
            }
            Path image = CURRENT_FOLDER.resolve(staticPath)
                    .resolve(imagePath).resolve(file.getOriginalFilename());
            System.out.print("IMG: " +  image);
            System.out.print("imagePath: " +  imagePath);
            try (OutputStream os = Files.newOutputStream(image)) {
                os.write(file.getBytes());
            }

            tesseract.setLanguage("vie");
            File convFile = convert(file);
            String text = tesseract.doOCR(convFile);
            OcrResult ocrResult = new OcrResult();
            ocrResult.setResult(text.split("\n"));
            ocrResult.setFileName(imagePath.resolve(file.getOriginalFilename()).toString());
            System.out.print(ocrResult);
            return ocrResult;
        } catch (Exception e) {
            System.err.println("Failed to upload file. Status code: " + e.getMessage());
        }
            return null;
    }

    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File("uploads/" + file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
