package be.ocrapi.controller;

import be.ocrapi.common.BaseResponse;
import be.ocrapi.common.BusinessErrorCode;
import be.ocrapi.common.BusinessException;
import be.ocrapi.model.Order;
import be.ocrapi.model.User;
import be.ocrapi.request.UserRequest;
import be.ocrapi.service.UserService;
import be.ocrapi.service.UserServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/user")
@Slf4j
public class UserController {
    @Autowired
    private UserServiceInterface userService;


    @GetMapping("show/{id}")
    public BaseResponse<?> findOne(@PathVariable Integer id) {
        try {
            return BaseResponse.ofSucceeded(userService.findById(id));
        } catch (Exception e) {
            log.debug("[USER CONTROLLER]------>error findOne", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[USER CONTROLLER]------>findOne", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @GetMapping("list")
    public BaseResponse<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") String page,
            @RequestParam(name = "page_size", required = false, defaultValue = "20") String page_size,
            @RequestParam(name = "status", required = false, defaultValue = "") String status,
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "email", required = false, defaultValue = "") String email,
            @RequestParam(name = "rank_id", required = false, defaultValue = "") String rank_id,
            @RequestParam(name = "salary_id", required = false, defaultValue = "") String salary_id,
            @RequestParam(name = "certificate_id", required = false, defaultValue = "") String certificate_id,
            @RequestParam(name = "room_id", required = false, defaultValue = "") String room_id,
            @RequestParam(name = "user_type", required = false, defaultValue = "") String user_type,
            @RequestParam(name = "type_id", required = false, defaultValue = "") String type_id
    ) {
        try {
            int number_page = 0;
            if(Integer.parseInt(page) > 1) {
                number_page = Integer.parseInt(page) - 1;
            }
//            var response = userService.findAll(number_page, Integer.parseInt(page_size));
            var users = userService.findAndCount(page, page_size,status, name, email, salary_id, rank_id, room_id, certificate_id, user_type);
            Integer total = userService.countTotalCondition(status, name, email, salary_id, rank_id, room_id, certificate_id, user_type);
            BaseResponse.Metadata paging = new BaseResponse.Metadata("", number_page ,  Integer.parseInt(page_size), Long.parseLong(total + ""), "", null);
            return BaseResponse.ofSucceeded().setData(users).setMeta(paging);
        } catch (Exception e) {
            log.debug("[USER CONTROLLER]------>error list", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[USER CONTROLLER]------>list", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @PostMapping("store")
    public BaseResponse<?> save(@RequestBody UserRequest data) {
        try {
            return BaseResponse.ofSucceeded(userService.save(data));
        } catch (Exception e) {
            log.debug("[USER CONTROLLER]------>error create", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, "Tạo mới thất bại", 400));
            log.error("[USER CONTROLLER]------>create", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @PutMapping("update/{id}")
    public BaseResponse<?> update(@PathVariable Integer id,@RequestBody UserRequest data) {
        try {
            return BaseResponse.ofSucceeded(userService.update(id, data));
        } catch (Exception e) {
            log.debug("[USER CONTROLLER]------>error update", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, "Cập nhật thất bại", 400));
            log.error("[USER CONTROLLER]------>update", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @DeleteMapping
    public void delete(@RequestBody User order) {
        userService.delete(order);
    }
}
