package be.ocrapi.service.Salary;

import be.ocrapi.request.SalaryRequest;
import be.ocrapi.response.Salary.ListSalaryResponse;
import be.ocrapi.response.Salary.SalaryResponse;
import org.springframework.data.domain.Page;

public interface SalaryServiceInterface {
    SalaryResponse findById(Integer id);
    ListSalaryResponse findAll(int page, int page_size);
    ListSalaryResponse findAndCount(int page, int page_size, String status, String user_id);
    SalaryRequest save(SalaryRequest order);
    SalaryRequest update(int id, SalaryRequest order);
    void delete(int order);
}
