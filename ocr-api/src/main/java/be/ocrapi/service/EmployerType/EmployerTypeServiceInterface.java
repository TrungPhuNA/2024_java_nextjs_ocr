package be.ocrapi.service.EmployerType;

import be.ocrapi.model.Category;
import be.ocrapi.model.EmployerType;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.request.EmployerTypeRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface EmployerTypeServiceInterface {
    Optional<EmployerType> findById(Integer id);
    Page<EmployerType> findAll(int page, int page_size);
    EmployerTypeRequest save(EmployerTypeRequest order);
    EmployerTypeRequest update(int id, EmployerTypeRequest order);
    void delete(int order);
}
