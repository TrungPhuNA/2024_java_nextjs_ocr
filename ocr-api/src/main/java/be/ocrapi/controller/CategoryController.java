package be.ocrapi.controller;

import be.ocrapi.common.BaseResponse;
import be.ocrapi.common.BusinessErrorCode;
import be.ocrapi.common.BusinessException;
import be.ocrapi.model.Category;
import be.ocrapi.model.Order;
import be.ocrapi.model.User;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.service.CategoryServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryServiceInterface categoryService;

    @GetMapping("show/{id}")
    public BaseResponse<?> findOne(@PathVariable Integer id) {
        try {
            return BaseResponse.ofSucceeded(categoryService.findById(id));
        } catch (Exception e) {
            log.debug("[Category CONTROLLER]------>error findOne", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[Category CONTROLLER]------>findOne", error);
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
            var response = categoryService.findAll(number_page, Integer.parseInt(page_size));
            return BaseResponse.ofSucceeded(response);
        } catch (Exception e) {
            log.debug("[Category CONTROLLER]------>error list", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[Category CONTROLLER]------>list", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @PostMapping("store")
    public BaseResponse<?> save(@RequestBody CategoryRequest data) {
        try {
            return BaseResponse.ofSucceeded(categoryService.save(data));
        } catch (Exception e) {
            log.debug("[Category CONTROLLER]------>error create", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[Category CONTROLLER]------>create", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @PutMapping("update/{id}")
    public BaseResponse<?> update(@PathVariable Integer id, @RequestBody CategoryRequest data) {
        try {
            return BaseResponse.ofSucceeded(categoryService.update(id, data));
        } catch (Exception e) {
            log.debug("[Category CONTROLLER]------>error update", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[Category CONTROLLER]------>update", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @DeleteMapping("delete/{id}")
    public BaseResponse delete(@PathVariable Integer id) {
        try {
            categoryService.delete(id);
            return BaseResponse.ofSucceeded();
        } catch (Exception e) {
            log.debug("[BonusAndDisciplineRequest CONTROLLER]------>error update", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, "Cập nhật thất bại", 400));
            log.error("[BonusAndDisciplineRequest CONTROLLER]------>update", error);
            return BaseResponse.ofFailed(error);
        }
    }
}
