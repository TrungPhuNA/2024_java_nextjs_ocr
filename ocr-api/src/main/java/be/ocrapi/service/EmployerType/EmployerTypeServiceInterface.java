package be.ocrapi.service.EmployerType;

import be.ocrapi.model.Category;
import be.ocrapi.model.EmployerType;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.request.EmployerTypeRequest;
import be.ocrapi.response.EmployerType.ListEmployerTypeResponse;
import be.ocrapi.response.EmployerType.TypeResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface EmployerTypeServiceInterface {
    TypeResponse findById(Integer id);
    ListEmployerTypeResponse findAll(int page, int page_size);
    EmployerTypeRequest save(EmployerTypeRequest order);
    EmployerTypeRequest update(int id, EmployerTypeRequest order);
    void delete(int order);
}
