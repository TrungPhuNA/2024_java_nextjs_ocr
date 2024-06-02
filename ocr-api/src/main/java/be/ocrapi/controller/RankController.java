package be.ocrapi.controller;

import be.ocrapi.common.BaseResponse;
import be.ocrapi.common.BusinessErrorCode;
import be.ocrapi.common.BusinessException;
import be.ocrapi.model.Rank;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.request.RankRequest;
import be.ocrapi.service.CategoryServiceInterface;
import be.ocrapi.service.Rank.RankServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/rank")
@Slf4j
public class RankController {
    @Autowired
    private RankServiceInterface service;

    @GetMapping("show/{id}")
    public BaseResponse<?> findOne(@PathVariable Integer id) {
        try {
            return BaseResponse.ofSucceeded(service.findById(id));
        } catch (Exception e) {
            log.debug("[RANK CONTROLLER]------>error findOne", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[RANK CONTROLLER]------>findOne", error);
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
            BaseResponse.Metadata paging = new BaseResponse.Metadata("", number_page ,  Integer.parseInt(page_size), response.getTotal(), "", null);

            return BaseResponse.ofSucceeded().setMeta(paging).setData(response.getData());
        } catch (Exception e) {
            log.debug("[RANK CONTROLLER]------>error list", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[RANK CONTROLLER]------>list", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @PostMapping("store")
    public BaseResponse<?> save(@RequestBody RankRequest data) {
        try {
            var newData = service.save(data);
            return BaseResponse.ofSucceeded(newData);
        } catch (Exception e) {
            log.debug("[RANK CONTROLLER]------>error create", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, "Tạo mới thất bại", 400));
            log.error("[RANK CONTROLLER]------>create", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @PutMapping("update/{id}")
    public BaseResponse<?> update(@PathVariable Integer id, @RequestBody RankRequest data) {
        try {
            return BaseResponse.ofSucceeded(service.update(id, data));
        } catch (Exception e) {
            log.debug("[RANK CONTROLLER]------>error update", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, "Cập nhật thất bại", 400));
            log.error("[RANK CONTROLLER]------>update", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
