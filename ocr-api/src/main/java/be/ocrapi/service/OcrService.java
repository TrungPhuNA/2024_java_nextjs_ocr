package be.ocrapi.service;

import be.ocrapi.common.BaseResponse;
import be.ocrapi.model.OcrResult;
// import com.cloudmersive.client.ImageOcrApi;
// import com.cloudmersive.client.model.ImageToTextResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
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
import java.util.List;

// import com.cloudmersive.client.invoker.ApiClient;
// import com.cloudmersive.client.invoker.ApiException;
// import com.cloudmersive.client.invoker.Configuration;
// import com.cloudmersive.client.invoker.auth.*;
// import com.cloudmersive.client.ConvertDocumentApi;

@Service
@Slf4j
public class OcrService {
    @Autowired
    private Tesseract tesseract;
    @Value("${api.upload.token}")
    private  String token;






    public OcrResult ocr(MultipartFile file) throws IOException, TesseractException {
        return null;
//        ApiClient defaultClient = Configuration.getDefaultApiClient();
//
//        ApiKeyAuth Apikey = (ApiKeyAuth) defaultClient.getAuthentication("Apikey");
//        Apikey.setApiKey("c9c31e07-11bc-4ab6-9b0d-47d878d27bb7");
//
//        ImageOcrApi apiInstance = new ImageOcrApi();
//        OcrResult o = new OcrResult();
//        String recognitionMode = "OPTIONAL";
//        String language = "VIE";
//        String preprocessing = "AUTO";
//        File newFile = convert(file);
//        log.error("config file=========> " , Apikey);
//
//        try {
//            ImageToTextResponse result = apiInstance.imageOcrPost(newFile, recognitionMode, language, preprocessing);
//            System.out.println(result);
//            log.debug("config result=========> " , result);
//            o.setResult(result);
//            System.out.println(result);
//
//        } catch (ApiException e) {
//            System.err.println("Exception when calling ImageOcrApi#imageOcrPost");
//            e.printStackTrace();
//
//        }
//        return  o;
////        tesseract.setLanguage("eng");
////        tesseract.setPageSegMode(1);
////        tesseract.setOcrEngineMode(1);
////        File convFile = convert(file);
////        String text = tesseract.doOCR(convFile);
////        OcrResult ocrResult = new OcrResult();
////        ocrResult.setResult(text.split("\n"));
////        System.out.print(ocrResult);
////        return ocrResult;
    }


    public OcrResult ocrV2(MultipartFile file) throws IOException, TesseractException {
        try {
            String uploadUrl = "https://testapi.cloudmersive.com/ocr/image/toText";
            System.out.println("token key----------> "+token);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            File newFile = convert(file);

            headers.set("Apikey", token);
            headers.set("accept", "application/json");

            // Create the request body with the file
            FileSystemResource resource = new FileSystemResource(newFile);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", resource);

            log.error("file--------> ", body);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            // Make the HTTP POST request
            ResponseEntity<String> response = restTemplate.postForEntity(uploadUrl, requestEntity, String.class);
            log.debug("response-------> ", response);
//             Handle the response
            if (response.getStatusCode().is2xxSuccessful()) {
                String data = response.getBody();
                System.out.println("File uploaded successfully!");
                log.debug("response-------> ", data);

                if(data != null) {
                    Gson gson = new Gson();
                    OcrResult myObject = gson.fromJson(data, OcrResult.class);
                    return myObject;
                }

            } else {
                System.err.println("Failed to upload file. Status code: " + response.getStatusCode());
            }
//
//            String result = restTemplate.postForEntity( uploadUrl, requestEntity,
//                    String.class).getBody().toString();

//            System.out.println(result);

//            o.setResult(result);
        } catch (Exception e) {
            System.err.println("error-----------> " + e.getMessage());
            e.printStackTrace();
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
