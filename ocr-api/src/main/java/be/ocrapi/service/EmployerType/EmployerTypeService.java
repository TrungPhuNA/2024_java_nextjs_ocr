package be.ocrapi.service.EmployerType;

import be.ocrapi.model.Category;
import be.ocrapi.model.EmployerType;
import be.ocrapi.model.Salary;
import be.ocrapi.model.User;
import be.ocrapi.repository.CategoryRepository;
import be.ocrapi.repository.EmployerTypeRepository;
import be.ocrapi.repository.SalaryRepository;
import be.ocrapi.repository.UserRepository;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.request.EmployerTypeRequest;
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
public class EmployerTypeService implements EmployerTypeServiceInterface {
    @Autowired
    private EmployerTypeRepository repository;


    @Override
    public Optional<EmployerType> findById(Integer id) {
        return repository.findById(id);
    }


    @Autowired
    private UserRepository userRepository;
    public EmployerType createOrUpdateData(EmployerTypeRequest dataRequest, EmployerType oldData) {
        EmployerType newData = oldData;

        if(oldData == null) {
            newData = new EmployerType();
            newData.setCreated_at(new Date());
        }
        User user = newData.getUser();
        if(dataRequest.getUser_id() != null) {
            user = userRepository.getById(dataRequest.getUser_id());
        }
        newData.setDescription(dataRequest.getDescription());
        newData.setStatus(dataRequest.getStatus());
        newData.setName(dataRequest.getName());
        newData.setUser(user);
        newData.setUpdated_at(dataRequest.getUpdated_at());
        return newData;
    }

    @Override
    public Page<EmployerType> findAll(int page, int page_size) {
        Pageable pageable = PageRequest.of(page, page_size);
        return repository.findAll(pageable);
    }

    @Override
    public EmployerTypeRequest save(EmployerTypeRequest dataRequest) {
        EmployerType o = this.createOrUpdateData(dataRequest, null);
        repository.save(o);
        return dataRequest;
    }

    @Override
    public EmployerTypeRequest update(int id, EmployerTypeRequest dataRequest) {
        var c = repository.getById(id);
        if(c != null) {
            EmployerType o = this.createOrUpdateData(dataRequest, c);

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
