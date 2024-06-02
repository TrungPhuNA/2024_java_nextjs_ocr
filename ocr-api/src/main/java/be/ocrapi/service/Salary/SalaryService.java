package be.ocrapi.service.Salary;

import be.ocrapi.model.Salary;
import be.ocrapi.model.User;
import be.ocrapi.repository.SalaryRepository;
import be.ocrapi.repository.UserRepository;
import be.ocrapi.request.SalaryRequest;
import be.ocrapi.response.MappingResponseDto;
import be.ocrapi.response.Salary.ListSalaryResponse;
import be.ocrapi.response.Salary.SalaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SalaryService implements SalaryServiceInterface {
    @Autowired
    private SalaryRepository repository;
    @Autowired
    private MappingResponseDto responseDto;

    @Override
    public SalaryResponse findById(Integer id) {
        return responseDto.getInfoSalary(repository.getById(id));
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
    public ListSalaryResponse findAll(int page, int page_size) {

        Pageable pageable = PageRequest.of(page, page_size);

        Page<Salary> salaries = repository.findAll(pageable);
        ListSalaryResponse dataListResponse = new ListSalaryResponse();
        List<SalaryResponse> data = new ArrayList<>();
        dataListResponse.setTotal(salaries.getTotalElements());
        if(salaries.isEmpty()) {
            dataListResponse.setData(new ArrayList<>());
            return dataListResponse;
        }
        for (Salary item: salaries) {
            data.add(responseDto.getInfoSalary(item));
        }
        dataListResponse.setData(data);
        return dataListResponse;
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
