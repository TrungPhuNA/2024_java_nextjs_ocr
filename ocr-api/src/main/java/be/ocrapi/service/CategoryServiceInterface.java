package be.ocrapi.service;

import be.ocrapi.model.Category;
import be.ocrapi.model.Order;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.request.OrderRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CategoryServiceInterface {
    Optional<Category> findById(Integer id);
    Page<Category> findAll(int page, int page_size);
    Category save(CategoryRequest order);
    Category update(int id, CategoryRequest order);
    void delete(int order);
}
