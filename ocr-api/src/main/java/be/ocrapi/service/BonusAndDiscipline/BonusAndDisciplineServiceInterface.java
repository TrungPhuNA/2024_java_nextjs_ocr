package be.ocrapi.service.BonusAndDiscipline;

import be.ocrapi.model.BonusAndDiscipline;
import be.ocrapi.model.Category;
import be.ocrapi.request.BonusAndDisciplineRequest;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.response.Bonus.BonusResponse;
import be.ocrapi.response.Bonus.ListBonusResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BonusAndDisciplineServiceInterface {
    BonusResponse findById(Integer id);
    ListBonusResponse findAll(int page, int page_size);
    BonusAndDisciplineRequest save(BonusAndDisciplineRequest data);
    BonusAndDisciplineRequest update(int id, BonusAndDisciplineRequest data);
    void delete(int order);
}
