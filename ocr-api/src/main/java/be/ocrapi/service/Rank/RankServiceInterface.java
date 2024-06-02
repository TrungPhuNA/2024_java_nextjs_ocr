package be.ocrapi.service.Rank;

import be.ocrapi.model.Rank;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.request.RankRequest;
import be.ocrapi.response.Rank.ListRankResponse;
import be.ocrapi.response.Rank.RankResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface RankServiceInterface {
    RankResponse findById(Integer id);
    ListRankResponse findAll(int page, int page_size);
    RankRequest save(RankRequest data);
    RankRequest update(int id, RankRequest data);
    void delete(int data_id);
}
