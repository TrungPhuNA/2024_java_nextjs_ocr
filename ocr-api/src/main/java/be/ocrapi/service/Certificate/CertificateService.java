package be.ocrapi.service.Certificate;

import be.ocrapi.model.Category;
import be.ocrapi.model.Certificate;
import be.ocrapi.model.Rank;
import be.ocrapi.model.User;
import be.ocrapi.repository.CategoryRepository;
import be.ocrapi.repository.CertificateRepository;
import be.ocrapi.repository.UserRepository;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.request.CertificateRequest;
import be.ocrapi.request.RankRequest;
import be.ocrapi.service.CategoryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CertificateService implements CertificateServiceInterface {
    @Autowired
    private CertificateRepository repository;


    @Override
    public Optional<Certificate> findById(Integer id) {
        return repository.findById(id);
    }


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
    public Page<Certificate> findAll(int page, int page_size) {
        Pageable pageable = PageRequest.of(page, page_size);
        return repository.findAll(pageable);
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
