package be.ocrapi.service.Room;

import be.ocrapi.model.Category;
import be.ocrapi.model.Certificate;
import be.ocrapi.model.Room;
import be.ocrapi.model.User;
import be.ocrapi.repository.CategoryRepository;
import be.ocrapi.repository.CertificateRepository;
import be.ocrapi.repository.RoomRepository;
import be.ocrapi.repository.UserRepository;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.request.CertificateRequest;
import be.ocrapi.request.RoomRequest;
import be.ocrapi.service.CategoryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class RoomService implements RoomServiceInterface {
    @Autowired
    private RoomRepository repository;


    @Override
    public Optional<Room> findById(Integer id) {
        return repository.findById(id);
    }


    @Autowired
    private UserRepository userRepository;
    public Room createOrUpdateData(RoomRequest dataRequest, Room oldData) {
        Room newData = oldData;

        if(oldData == null) {
            newData = new Room();
            newData.setCreated_at(new Date());
        }
//        User user = newData.getUser();
//        if(dataRequest.getUser_id() != null) {
//            user = userRepository.getById(dataRequest.getUser_id());
//        }
        newData.setName(dataRequest.getName());
        newData.setStatus(dataRequest.getStatus());
        newData.setDescription(dataRequest.getDescription());
//        newData.setUser(user);
        newData.setUpdated_at(dataRequest.getUpdated_at());
        return newData;
    }

    @Override
    public Page<Room> findAll(int page, int page_size) {
        Pageable pageable = PageRequest.of(page, page_size);
        return repository.findAll(pageable);
    }

    @Override
    public RoomRequest save(RoomRequest dataRequest) {
        Room o = this.createOrUpdateData(dataRequest, null);
        repository.save(o);
        return dataRequest;
    }

    @Override
    public RoomRequest update(int id, RoomRequest dataRequest) {
        var c = repository.getById(id);
        if(c != null) {
            Room o = this.createOrUpdateData(dataRequest, c);

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
