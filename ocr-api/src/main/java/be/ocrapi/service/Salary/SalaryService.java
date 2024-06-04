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

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public static Date parseDate(String dateString, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Invalid date format: " + e.getMessage());
            return null;
        }
    }
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
        User admin = newData.getUpdatedBy();
        if(dataRequest.getUser_id() != null) {
            admin = userRepository.getById(dataRequest.getUpdated_by());
        }
        if(dataRequest.getFrom_date() != null && !dataRequest.getFrom_date().isEmpty()) {
            Date fromDate = this.parseDate(dataRequest.getFrom_date(), "yyyy-MM-dd");
            newData.setFrom_date(fromDate);
        }
        if(dataRequest.getTo_date() != null && !dataRequest.getTo_date().isEmpty()) {
            Date date = this.parseDate(dataRequest.getTo_date(), "yyyy-MM-dd");
            newData.setTo_date(date);
        }
        newData.setSalary(dataRequest.getSalary());
        newData.setAllowance(dataRequest.getAllowance());
        newData.setWorkday(dataRequest.getWorkday());
        newData.setReceive_salary(dataRequest.getReceive_salary());
        newData.setStatus(dataRequest.getStatus());
        newData.setUser(user);
        newData.setUpdatedBy(admin);
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
