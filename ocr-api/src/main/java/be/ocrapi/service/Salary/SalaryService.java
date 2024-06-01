package be.ocrapi.service.Salary;

import be.ocrapi.model.Category;
import be.ocrapi.model.Room;
import be.ocrapi.model.Salary;
import be.ocrapi.model.User;
import be.ocrapi.repository.CategoryRepository;
import be.ocrapi.repository.RoomRepository;
import be.ocrapi.repository.SalaryRepository;
import be.ocrapi.repository.UserRepository;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.request.RoomRequest;
import be.ocrapi.request.SalaryRequest;
import be.ocrapi.service.CategoryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class SalaryService implements SalaryServiceInterface {
    @Autowired
    private SalaryRepository repository;


    @Override
    public Optional<Salary> findById(Integer id) {
        return repository.findById(id);
    }


    @Autowired
    private UserRepository userRepository;
    public Salary createOrUpdateData(SalaryRequest dataRequest, Salary oldData) {
        Salary newData = oldData;

        if(oldData == null) {
            newData = new Salary();
            newData.setCreated_at(new Date());
        }
        User user = newData.getUser();
        if(dataRequest.getUser_id() != null) {
            user = userRepository.getById(dataRequest.getUser_id());
        }
        newData.setSalary(dataRequest.getSalary());
        newData.setStatus(dataRequest.getStatus());
        newData.setUser(user);
        newData.setUpdated_at(dataRequest.getUpdated_at());
        return newData;
    }

    @Override
    public Page<Salary> findAll(int page, int page_size) {
        Pageable pageable = PageRequest.of(page, page_size);
        return repository.findAll(pageable);
    }

    @Override
    public SalaryRequest save(SalaryRequest dataRequest) {
        Salary o = this.createOrUpdateData(dataRequest, null);
        repository.save(o);
        return dataRequest;
    }

    @Override
    public SalaryRequest update(int id, SalaryRequest dataRequest) {
        var c = repository.getById(id);
        if(c != null) {
            Salary o = this.createOrUpdateData(dataRequest, c);

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
