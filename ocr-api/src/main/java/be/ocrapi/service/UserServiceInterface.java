package be.ocrapi.service;

import be.ocrapi.model.Order;
import be.ocrapi.model.User;
import be.ocrapi.request.UserRequest;
import be.ocrapi.response.LoginResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    Optional<User> findById(Integer id);
    LoginResponse login(UserRequest data);
    Page<User> findAll(int page, int page_size);

    User save(UserRequest user);
    User update(int id, UserRequest user);
    void delete(User user);
}
