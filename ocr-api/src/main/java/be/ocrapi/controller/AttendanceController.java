package be.ocrapi.controller;

import be.ocrapi.common.BaseResponse;
import be.ocrapi.common.BusinessErrorCode;
import be.ocrapi.common.BusinessException;
import be.ocrapi.request.AttendanceRequest;
import be.ocrapi.response.Attendance.AttendanceResponse;
import be.ocrapi.service.Attendance.AttendanceServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/attendance")
@Slf4j
public class AttendanceController {
    @Autowired
    private AttendanceServiceInterface service;

    @GetMapping("show/{id}")
    public BaseResponse<?> findOne(@PathVariable Integer id) {
        try {
            return BaseResponse.ofSucceeded(service.findById(id));
        } catch (Exception e) {
            log.debug("[AttendanceServiceInterface CONTROLLER]------>error findOne", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[AttendanceServiceInterface CONTROLLER]------>findOne", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @GetMapping("list")
    public BaseResponse<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") String page,
            @RequestParam(name = "page_size", required = false, defaultValue = "20") String page_size,
            @RequestParam(name = "from_date", required = false, defaultValue = "") String from_date,
            @RequestParam(name = "to_date", required = false, defaultValue = "") String to_date,
            @RequestParam(name = "user_id", required = false, defaultValue = "") String user_id,
            @RequestParam(name = "status", required = false, defaultValue = "") String status,
            @RequestParam(name = "type", required = false, defaultValue = "") String type
    ) {
        try {
            int number_page = 0;
            if(Integer.parseInt(page) > 1) {
                number_page = Integer.parseInt(page) - 1;
            }
            var response = service.findAndCount(number_page, Integer.parseInt(page_size),from_date, to_date, status, user_id, type);

            BaseResponse.Metadata paging = new BaseResponse.Metadata("", number_page ,  Integer.parseInt(page_size), response.getTotal(), "", null);

            return BaseResponse.ofSucceeded().setMeta(paging).setData(response.getData());
        } catch (Exception e) {
            log.debug("[AttendanceServiceInterface CONTROLLER]------>error list", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[AttendanceServiceInterface CONTROLLER]------>list", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @PostMapping("store")
    public BaseResponse<?> save(@RequestBody AttendanceRequest data) {
        try {
            var response = service.findAndCount(1, 1, data.getCheck_in(), data.getCheck_in(), null, data.getUser_id().toString(), null);
            if(response.getTotal() > 0) {
                throw new RuntimeException("Tài khoản đã chấm công trong thời gian này!");
            }
            var newData = service.save(data);
            return BaseResponse.ofSucceeded(newData);
        } catch (Exception e) {
            log.debug("[AttendanceServiceInterface CONTROLLER]------>error create", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[AttendanceServiceInterface CONTROLLER]------>create", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @PutMapping("update/{id}")
    public BaseResponse<?> update(@PathVariable Integer id, @RequestBody AttendanceRequest data) {
        try {
            var response = service.findAndCount(1, 1, data.getCheck_in(), data.getCheck_in(), null, data.getUser_id().toString(), null);
            if(response.getTotal() > 0) {
                AttendanceResponse oldData = response.getData().get(0);
                if(oldData != null && oldData.getId() != id) {
                    throw new RuntimeException("Tài khoản đã chấm công trong thời gian này!");
                }
            }
            return BaseResponse.ofSucceeded(service.update(id, data));
        } catch (Exception e) {
            log.debug("[AttendanceServiceInterface CONTROLLER]------>error update", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[AttendanceServiceInterface CONTROLLER]------>update", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @DeleteMapping("delete/{id}")
    public BaseResponse delete(@PathVariable Integer id) {
        try {
            service.delete(id);
            return BaseResponse.ofSucceeded();
        } catch (Exception e) {
            log.debug("[AttendanceServiceInterface CONTROLLER]------>error update", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, "Xóa thất bại", 400));
            log.error("[AttendanceServiceInterface CONTROLLER]------>update", error);
            return BaseResponse.ofFailed(error);
        }
    }
}
