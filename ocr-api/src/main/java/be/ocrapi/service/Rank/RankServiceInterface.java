package be.ocrapi.service.Rank;

import be.ocrapi.model.Rank;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.request.RankRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface RankServiceInterface {
    Optional<Rank> findById(Integer id);
    Page<Rank> findAll(int page, int page_size);
    RankRequest save(RankRequest data);
    RankRequest update(int id, RankRequest data);
    void delete(int data_id);
}
