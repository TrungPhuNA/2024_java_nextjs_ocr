package be.ocrapi.service.WorkingTask;

import be.ocrapi.model.Salary;
import be.ocrapi.model.User;
import be.ocrapi.model.WorkingTasks;
import be.ocrapi.repository.SalaryRepository;
import be.ocrapi.repository.UserRepository;
import be.ocrapi.repository.WorkingTaskRepository;
import be.ocrapi.request.SalaryRequest;
import be.ocrapi.request.WorkingTaskRequest;
import be.ocrapi.service.Salary.SalaryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class WorkingTaskService implements WorkingTaskServiceInterface {
    @Autowired
    private WorkingTaskRepository repository;


    @Override
    public Optional<WorkingTasks> findById(Integer id) {
        return repository.findById(id);
    }


    @Autowired
    private UserRepository userRepository;


    public static Date parseDate(String dateString, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Invalid date format: " + e.getMessage());
            return null;
        }
    }
    public WorkingTasks createOrUpdateData(WorkingTaskRequest dataRequest, WorkingTasks oldData) {
        WorkingTasks newData = oldData;

        if(oldData == null) {
            newData = new WorkingTasks();
            newData.setCreated_at(new Date());
        }
        User user = newData.getUser();
        if(dataRequest.getUser_id() != null) {
            user = userRepository.getById(dataRequest.getUser_id());
        }
        newData.setBonus(dataRequest.getBonus());
        if(dataRequest.getFrom_date() != null && !dataRequest.getFrom_date().isEmpty()) {
            Date fromDate = this.parseDate(dataRequest.getFrom_date(), "yyyy-MM-dd");
            newData.setFrom_date(fromDate);
        }
        if(dataRequest.getTo_date() != null && !dataRequest.getTo_date().isEmpty()) {
            Date date = this.parseDate(dataRequest.getTo_date(), "yyyy-MM-dd");
            newData.setTo_date(date);
        }
        newData.setStatus(dataRequest.getStatus());
        newData.setDescription(dataRequest.getDescription());
        newData.setName(dataRequest.getName());
        newData.setUser(user);
        newData.setUpdated_at(dataRequest.getUpdated_at());
        return newData;
    }

    @Override
    public Page<WorkingTasks> findAll(int page, int page_size) {
        Pageable pageable = PageRequest.of(page, page_size);
        return repository.findAll(pageable);
    }

    @Override
    public WorkingTaskRequest save(WorkingTaskRequest dataRequest) {
        WorkingTasks o = this.createOrUpdateData(dataRequest, null);
        repository.save(o);
        return dataRequest;
    }

    @Override
    public WorkingTaskRequest update(int id, WorkingTaskRequest dataRequest) {
        var c = repository.getById(id);
        if(c != null) {
            WorkingTasks o = this.createOrUpdateData(dataRequest, c);

            repository.save(o);
            return dataRequest;
        }
        throw new RuntimeException("Không tìm thấy dữ liệu");
    }


    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}
