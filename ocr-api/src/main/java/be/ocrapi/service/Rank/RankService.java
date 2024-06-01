package be.ocrapi.service.Rank;

import be.ocrapi.model.Category;
import be.ocrapi.model.Rank;
import be.ocrapi.model.User;
import be.ocrapi.repository.CategoryRepository;
import be.ocrapi.repository.RankRepository;
import be.ocrapi.repository.UserRepository;
import be.ocrapi.request.CategoryRequest;
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
public class RankService implements RankServiceInterface {
    @Autowired
    private RankRepository repository;

    @Autowired
    private UserRepository userRepository;


    public Rank createOrUpdateData(RankRequest dataRequest, Rank oldData) {
        Rank newData = oldData;

        if(oldData == null) {
            newData = new Rank();
            newData.setCreated_at(new Date());
        }
        User user = newData.getUser();
        if(dataRequest.getUser_id() != null) {
            user = userRepository.getById(dataRequest.getUser_id());
        }
        newData.setName(dataRequest.getName());
        newData.setStatus(dataRequest.getStatus());
        newData.setCode(dataRequest.getCode());
        newData.setSalary(dataRequest.getSalary());
        newData.setUser(user);
        newData.setUpdated_at(dataRequest.getUpdated_at());
        return newData;
    }


    @Override
    public Optional<Rank> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Page<Rank> findAll(int page, int page_size) {
        Pageable pageable = PageRequest.of(page, page_size);
        return repository.findAll(pageable);
    }

    @Override
    public RankRequest save(RankRequest dataRequest) {
        Rank o = this.createOrUpdateData(dataRequest, null);
        Rank newRank = repository.save(o);
        return dataRequest;
    }

    @Override
    public RankRequest update(int id, RankRequest dataRequest) {
        var c = repository.getById(id);
        if(c != null) {
            Rank o = this.createOrUpdateData(dataRequest, c);
            Rank newRank = repository.save(o);
            return dataRequest;
        }
        throw new RuntimeException("Không tìm thấy dữ liệu");
    }


    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

}
