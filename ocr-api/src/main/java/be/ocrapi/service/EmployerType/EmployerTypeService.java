package be.ocrapi.service.EmployerType;

import be.ocrapi.model.*;
import be.ocrapi.repository.CategoryRepository;
import be.ocrapi.repository.EmployerTypeRepository;
import be.ocrapi.repository.SalaryRepository;
import be.ocrapi.repository.UserRepository;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.request.EmployerTypeRequest;
import be.ocrapi.request.SalaryRequest;
import be.ocrapi.response.EmployerType.ListEmployerTypeResponse;
import be.ocrapi.response.EmployerType.TypeResponse;
import be.ocrapi.response.MappingResponseDto;
import be.ocrapi.response.Room.ListRoomResponse;
import be.ocrapi.response.Room.RoomResponse;
import be.ocrapi.service.CategoryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EmployerTypeService implements EmployerTypeServiceInterface {
    @Autowired
    private EmployerTypeRepository repository;

    @Autowired
    private MappingResponseDto responseDto;



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
    public TypeResponse findById(Integer id) {
        return responseDto.getInfoEmployerType(repository.getById(id));
    }

    @Override
    public ListEmployerTypeResponse findAll(int page, int page_size) {
        Pageable pageable = PageRequest.of(page, page_size);
        Page<EmployerType> results = repository.findAll(pageable);

        ListEmployerTypeResponse dataListResponse = new ListEmployerTypeResponse();
        dataListResponse.setTotal(results.getTotalElements());

        if(results.isEmpty()) {
            dataListResponse.setData(new ArrayList<>());
            return dataListResponse;
        }

        List<TypeResponse> data = new ArrayList<>();
        for (EmployerType item: results) {
            data.add(responseDto.getInfoEmployerType(item));
        }
        dataListResponse.setData(data);
        return dataListResponse;
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
