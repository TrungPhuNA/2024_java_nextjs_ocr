package be.ocrapi.service.Certificate;

import be.ocrapi.model.*;
import be.ocrapi.repository.CategoryRepository;
import be.ocrapi.repository.CertificateRepository;
import be.ocrapi.repository.UserRepository;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.request.CertificateRequest;
import be.ocrapi.request.RankRequest;
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
public class CertificateService implements CertificateServiceInterface {
    @Autowired
    private CertificateRepository repository;

    @Autowired
    private MappingResponseDto responseDto;


    @Autowired
    private UserRepository userRepository;
    public Certificate createOrUpdateData(CertificateRequest dataRequest, Certificate oldData) {
        Certificate newData = oldData;

        if(oldData == null) {
            newData = new Certificate();
            newData.setCreated_at(new Date());
        }
        User user = newData.getUser();
        if(dataRequest.getUser_id() != null) {
            user = userRepository.getById(dataRequest.getUser_id());
        }
        newData.setName(dataRequest.getName());
        newData.setStatus(dataRequest.getStatus());
        newData.setDescription(dataRequest.getDescription());
        newData.setUser(user);
        newData.setUpdated_at(dataRequest.getUpdated_at());
        return newData;
    }

    @Override
    public CertificateResponse findById(Integer id) {
        return responseDto.getInfoCertificate(repository.getById(id));
    }

    @Override
    public ListCertificateResponse findAll(int page, int page_size) {
        Pageable pageable = PageRequest.of(page, page_size);
        Page<Certificate> results = repository.findAll(pageable);

        ListCertificateResponse dataListResponse = new ListCertificateResponse();
        dataListResponse.setTotal(results.getTotalElements());

        if(results.isEmpty()) {
            dataListResponse.setData(new ArrayList<>());
            return dataListResponse;
        }

        List<CertificateResponse> data = new ArrayList<>();
        for (Certificate item: results) {
            data.add(responseDto.getInfoCertificate(item));
        }
        dataListResponse.setData(data);
        return dataListResponse;
    }

    @Override
    public CertificateRequest save(CertificateRequest dataRequest) {
        Certificate o = this.createOrUpdateData(dataRequest, null);
        repository.save(o);
        return dataRequest;
    }

    @Override
    public CertificateRequest update(int id, CertificateRequest dataRequest) {
        var c = repository.getById(id);
        if(c != null) {
            Certificate o = this.createOrUpdateData(dataRequest, c);
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
