package be.ocrapi.repository;

import be.ocrapi.model.BonusAndDiscipline;
import be.ocrapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BonusAndDisciplineRepository extends JpaRepository<BonusAndDiscipline, Integer> {


}