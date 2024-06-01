package be.ocrapi.controller;

import be.ocrapi.common.BaseResponse;
import be.ocrapi.common.BusinessErrorCode;
import be.ocrapi.common.BusinessException;
import be.ocrapi.request.CertificateRequest;
import be.ocrapi.request.RankRequest;
import be.ocrapi.service.Certificate.CertificateServiceInterface;
import be.ocrapi.service.Rank.RankServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/certificate")
@Slf4j
public class CertificateController {
    @Autowired
    private CertificateServiceInterface service;

    @GetMapping("show/{id}")
    public BaseResponse<?> findOne(@PathVariable Integer id) {
        try {
            return BaseResponse.ofSucceeded(service.findById(id));
        } catch (Exception e) {
            log.debug("[CertificateController CONTROLLER]------>error findOne", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[CertificateController CONTROLLER]------>findOne", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @GetMapping("list")
    public BaseResponse<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") String page,
            @RequestParam(name = "page_size", required = false, defaultValue = "20") String page_size
    ) {
        try {
            int number_page = 0;
            if(Integer.parseInt(page) > 1) {
                number_page = Integer.parseInt(page) - 1;
            }
            var response = service.findAll(number_page, Integer.parseInt(page_size));
            return BaseResponse.ofSucceeded(response);
        } catch (Exception e) {
            log.debug("[CertificateController CONTROLLER]------>error list", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[CertificateController CONTROLLER]------>list", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @PostMapping("store")
    public BaseResponse<?> save(@RequestBody CertificateRequest data) {
        try {
            var newData = service.save(data);
            return BaseResponse.ofSucceeded(newData);
        } catch (Exception e) {
            log.debug("[CertificateController CONTROLLER]------>error create", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, "Tạo mới thất bại", 400));
            log.error("[CertificateController CONTROLLER]------>create", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @PutMapping("update/{id}")
    public BaseResponse<?> update(@PathVariable Integer id, @RequestBody CertificateRequest data) {
        try {
            return BaseResponse.ofSucceeded(service.update(id, data));
        } catch (Exception e) {
            log.debug("[CertificateController CONTROLLER]------>error update", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, "Cập nhật thất bại", 400));
            log.error("[CertificateController CONTROLLER]------>update", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
