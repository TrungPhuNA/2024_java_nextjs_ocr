package be.ocrapi.service.BonusAndDiscipline;

import be.ocrapi.model.BonusAndDiscipline;
import be.ocrapi.model.Category;
import be.ocrapi.request.BonusAndDisciplineRequest;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.response.Bonus.BonusResponse;
import be.ocrapi.response.Bonus.ListBonusResponse;
import be.ocrapi.response.Salary.ListSalaryResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BonusAndDisciplineServiceInterface {
    BonusResponse findById(Integer id);
    ListBonusResponse findAll(int page, int page_size);
    BonusAndDisciplineRequest save(BonusAndDisciplineRequest data);
    ListBonusResponse findAndCount(int page, int page_size, String status, String user_id);

    BonusAndDisciplineRequest update(int id, BonusAndDisciplineRequest data);
    void delete(int order);
}
