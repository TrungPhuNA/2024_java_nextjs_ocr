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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/users")
@Slf4j
public class UserController {
    @Autowired
    private UserServiceInterface userService;

//    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;

    @GetMapping("{id}")
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

    @GetMapping
    public BaseResponse<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") String page,
            @RequestParam(name = "page_size", required = false, defaultValue = "20") String page_size
    ) {
        try {
            int number_page = 0;
            if(Integer.parseInt(page) > 1) {
                number_page = Integer.parseInt(page) - 1;
            }
            var response = userService.findAll(number_page, Integer.parseInt(page_size));
            return BaseResponse.ofSucceeded(response);
        } catch (Exception e) {
            log.debug("[USER CONTROLLER]------>error list", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[USER CONTROLLER]------>list", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @PostMapping
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

//    @PutMapping
//    public BaseResponse<?> update(@RequestBody UserRequest data) {
//        try {
//            return BaseResponse.ofSucceeded(userService.update(data));
//        } catch (Exception e) {
//            log.debug("[USER CONTROLLER]------>error update", e);
//            String message = e.getMessage();
//            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
//            log.error("[USER CONTROLLER]------>update", error);
//            return BaseResponse.ofFailed(error);
//        }
//    }

    @DeleteMapping
    public void delete(@RequestBody User order) {
        userService.delete(order);
    }
}
