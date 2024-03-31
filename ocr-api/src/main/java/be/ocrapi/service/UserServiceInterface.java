package be.ocrapi.service;

import be.ocrapi.model.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    Optional<User> findUser(Integer id);
    List<User> findAll();
    User save(User user);
    User update(User user);
    void delete(User user);
}
