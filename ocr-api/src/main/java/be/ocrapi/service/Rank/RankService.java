package be.ocrapi.service.Rank;

import be.ocrapi.model.Category;
import be.ocrapi.model.Rank;
import be.ocrapi.model.Room;
import be.ocrapi.model.User;
import be.ocrapi.repository.CategoryRepository;
import be.ocrapi.repository.RankRepository;
import be.ocrapi.repository.UserRepository;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.request.RankRequest;
import be.ocrapi.response.MappingResponseDto;
import be.ocrapi.response.Rank.ListRankResponse;
import be.ocrapi.response.Rank.RankResponse;
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
public class RankService implements RankServiceInterface {
    @Autowired
    private RankRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MappingResponseDto responseDto;


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
    public RankResponse findById(Integer id) {
        return responseDto.getInfoRank(repository.getById(id));
    }

    @Override
    public ListRankResponse findAll(int page, int page_size) {
        Pageable pageable = PageRequest.of(page, page_size);
        Page<Rank> results = repository.findAll(pageable);

        ListRankResponse dataListResponse = new ListRankResponse();
        dataListResponse.setTotal(results.getTotalElements());

        if(results.isEmpty()) {
            dataListResponse.setData(new ArrayList<>());
            return dataListResponse;
        }

        List<RankResponse> data = new ArrayList<>();
        for (Rank item: results) {
            data.add(responseDto.getInfoRank(item));
        }
        dataListResponse.setData(data);
        return dataListResponse;
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
