package be.ocrapi.service.WorkingTask;

import be.ocrapi.model.Salary;
import be.ocrapi.model.WorkingTasks;
import be.ocrapi.request.SalaryRequest;
import be.ocrapi.request.WorkingTaskRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface WorkingTaskServiceInterface {
    Optional<WorkingTasks> findById(Integer id);
    Page<WorkingTasks> findAll(int page, int page_size);
    WorkingTaskRequest save(WorkingTaskRequest order);
    WorkingTaskRequest update(int id, WorkingTaskRequest order);
    void delete(int order);
}
