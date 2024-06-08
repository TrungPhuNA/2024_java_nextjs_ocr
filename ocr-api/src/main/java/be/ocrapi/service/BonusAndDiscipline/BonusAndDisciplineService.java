package be.ocrapi.service.BonusAndDiscipline;

import be.ocrapi.model.*;
import be.ocrapi.repository.BonusAndDisciplineRepository;
import be.ocrapi.repository.CategoryRepository;
import be.ocrapi.repository.EmployerTypeRepository;
import be.ocrapi.repository.UserRepository;
import be.ocrapi.request.BonusAndDisciplineRequest;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.request.EmployerTypeRequest;
import be.ocrapi.response.Bonus.BonusResponse;
import be.ocrapi.response.Bonus.ListBonusResponse;
import be.ocrapi.response.Certificate.CertificateResponse;
import be.ocrapi.response.Certificate.ListCertificateResponse;
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
public class BonusAndDisciplineService implements BonusAndDisciplineServiceInterface {
    @Autowired
    private BonusAndDisciplineRepository repository;

    @Autowired
    private MappingResponseDto responseDto;


    @Autowired
    private UserRepository userRepository;
    public BonusAndDiscipline createOrUpdateData(BonusAndDisciplineRequest dataRequest, BonusAndDiscipline oldData) {
        BonusAndDiscipline newData = oldData;
        if(oldData == null) {
            newData = new BonusAndDiscipline();
            newData.setCreated_at(new Date());
        }
        User user = newData.getUser();
        if(dataRequest.getUser_id() != null) {
            user = userRepository.getById(dataRequest.getUser_id());
        }
        User updatedBy = newData.getUpdatedBy();
        if(dataRequest.getUpdated_by() != null) {
            updatedBy = userRepository.getById(dataRequest.getUpdated_by());
        }
        newData.setContent(dataRequest.getContent());
        newData.setName(dataRequest.getName());
        newData.setType(dataRequest.getType());
        newData.setStatus(dataRequest.getStatus());
        newData.setDataValue(dataRequest.getData_value());
        newData.setUser(user);
        newData.setUpdatedBy(updatedBy);
        newData.setUpdated_at(dataRequest.getUpdated_at());
        return newData;
    }

    @Override
    public BonusResponse findById(Integer id) {
        return responseDto.getInfoBonus(repository.getById(id));
    }

    @Override
    public ListBonusResponse findAll(int page, int page_size) {
        Pageable pageable = PageRequest.of(page, page_size);
        Page<BonusAndDiscipline> results = repository.findAll(pageable);

        ListBonusResponse dataListResponse = new ListBonusResponse();
        dataListResponse.setTotal(results.getTotalElements());

        if(results.isEmpty()) {
            dataListResponse.setData(new ArrayList<>());
            return dataListResponse;
        }

        List<BonusResponse> data = new ArrayList<>();
        for (BonusAndDiscipline item: results) {
            data.add(responseDto.getInfoBonus(item));
        }
        dataListResponse.setData(data);
        return dataListResponse;
    }

    @Override
    public BonusAndDisciplineRequest save(BonusAndDisciplineRequest dataRequest) {
        BonusAndDiscipline o = this.createOrUpdateData(dataRequest, null);
        repository.save(o);
        return dataRequest;
    }

    @Override
    public BonusAndDisciplineRequest update(int id, BonusAndDisciplineRequest dataRequest) {
        var c = repository.getById(id);
        if(c != null) {
            BonusAndDiscipline o = this.createOrUpdateData(dataRequest, c);

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
