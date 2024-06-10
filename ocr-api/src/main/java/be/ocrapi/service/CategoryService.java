package be.ocrapi.service;

import be.ocrapi.model.Category;
import be.ocrapi.model.Order;
import be.ocrapi.repository.CategoryRepository;
import be.ocrapi.request.CategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CategoryServiceInterface {
    @Autowired
    private CategoryRepository categoryRepository;



    @Override
    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Page<Category> findAll(int page, int page_size) {
        Pageable pageable = PageRequest.of(page, page_size);
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category save(CategoryRequest order) {
        Category o = new Category();
        o.setName(order.getName());
        o.setStatus(order.getStatus());
        return categoryRepository.save(o);
    }

    @Override
    public Category update(int id, CategoryRequest order) {
        var c = categoryRepository.getById(id);
        if(c != null) {
            c.setName(order.getName());
            c.setStatus(order.getStatus());
            return categoryRepository.save(c);
        }
        throw new RuntimeException("Cập nhật thất bại");
    }


    @Override
    public void delete(int id) {
        categoryRepository.deleteById(id);
    }
}
