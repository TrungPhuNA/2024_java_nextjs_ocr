package be.ocrapi.service;

import be.ocrapi.model.Order;
import be.ocrapi.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    Optional<User> findById(Integer id);
    Page<User> findAll(int page, int page_size);

    User save(User user);
    User update(User user);
    void delete(User user);
}
