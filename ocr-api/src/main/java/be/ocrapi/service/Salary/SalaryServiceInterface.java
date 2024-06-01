package be.ocrapi.service.Salary;

import be.ocrapi.model.Category;
import be.ocrapi.model.Salary;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.request.SalaryRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface SalaryServiceInterface {
    Optional<Salary> findById(Integer id);
    Page<Salary> findAll(int page, int page_size);
    SalaryRequest save(SalaryRequest order);
    SalaryRequest update(int id, SalaryRequest order);
    void delete(int order);
}
