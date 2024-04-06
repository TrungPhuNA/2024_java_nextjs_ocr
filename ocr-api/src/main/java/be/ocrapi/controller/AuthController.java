package be.ocrapi.controller;

import be.ocrapi.common.BaseResponse;
import be.ocrapi.common.BusinessErrorCode;
import be.ocrapi.common.BusinessException;
import be.ocrapi.model.Category;
import be.ocrapi.model.User;
import be.ocrapi.request.UserRequest;
import be.ocrapi.service.CategoryServiceInterface;
import be.ocrapi.service.UserService;
import be.ocrapi.service.UserServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/auth")
@Slf4j
public class AuthController {
    @Autowired
    private UserServiceInterface userService;

    @GetMapping("profile/{id}")
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

    @PostMapping("register")
    public BaseResponse<?> save(@RequestBody UserRequest data) {
        try {
            return BaseResponse.ofSucceeded(userService.save(data));
        } catch (Exception e) {
            log.debug("[USER CONTROLLER]------>error create", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[USER CONTROLLER]------>create", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @PostMapping("login")
    public BaseResponse<?> login(@RequestBody UserRequest data) {
        try {
            return BaseResponse.ofSucceeded(userService.login(data));
        } catch (Exception e) {
            log.debug("[AUth CONTROLLER]------>error login", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[USER CONTROLLER]------>create", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @PutMapping("profile/{id}")
    public BaseResponse<?> update(@PathVariable Integer id, @RequestBody UserRequest data) {
        try {
            return BaseResponse.ofSucceeded(userService.update(id,data));
        } catch (Exception e) {
            log.debug("[USER CONTROLLER]------>error create", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[USER CONTROLLER]------>create", error);
            return BaseResponse.ofFailed(error);
        }
    }
}
