package be.ocrapi.service;

import be.ocrapi.model.Category;
import be.ocrapi.model.Order;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CategoryServiceInterface {
    Optional<Category> findById(Integer id);
    Page<Category> findAll(int page, int page_size);
    Category save(Category category);
    Category update(Category category);
    void delete(Category category);
}
