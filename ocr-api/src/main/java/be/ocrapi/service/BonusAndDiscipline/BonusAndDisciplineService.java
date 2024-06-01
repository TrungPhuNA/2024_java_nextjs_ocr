package be.ocrapi.service.BonusAndDiscipline;

import be.ocrapi.model.BonusAndDiscipline;
import be.ocrapi.model.Category;
import be.ocrapi.model.EmployerType;
import be.ocrapi.model.User;
import be.ocrapi.repository.BonusAndDisciplineRepository;
import be.ocrapi.repository.CategoryRepository;
import be.ocrapi.repository.EmployerTypeRepository;
import be.ocrapi.repository.UserRepository;
import be.ocrapi.request.BonusAndDisciplineRequest;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.request.EmployerTypeRequest;
import be.ocrapi.service.CategoryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class BonusAndDisciplineService implements BonusAndDisciplineServiceInterface {
    @Autowired
    private BonusAndDisciplineRepository repository;

    @Override
    public Optional<BonusAndDiscipline> findById(Integer id) {
        return repository.findById(id);
    }


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
        newData.setContent(dataRequest.getContent());
        newData.setName(dataRequest.getName());
        newData.setStatus(dataRequest.getStatus());
        newData.setUser(user);
        newData.setUpdated_at(dataRequest.getUpdated_at());
        return newData;
    }

    @Override
    public Page<BonusAndDiscipline> findAll(int page, int page_size) {
        Pageable pageable = PageRequest.of(page, page_size);
        return repository.findAll(pageable);
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
