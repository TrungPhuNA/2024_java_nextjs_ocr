package be.ocrapi.service;

import be.ocrapi.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryServiceInterface {
    Optional<Category> findUser(Integer id);
    List<Category> findAll();
    Category save(Category category);
    Category update(Category category);
    void delete(Category category);
}
