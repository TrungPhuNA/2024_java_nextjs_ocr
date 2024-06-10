package be.ocrapi.repository;

import be.ocrapi.model.BonusAndDiscipline;
import be.ocrapi.model.Salary;
import be.ocrapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BonusAndDisciplineRepository extends JpaRepository<BonusAndDiscipline, Integer> {


    @Query(value = "Select * from bonus_disciplines " +
            " WHERE TRUE " +
            "AND (:user_id is null or :user_id ='' or user_id =:user_id) " +
            "AND (:type is null or :type ='' or type =:type) " +
            " AND (:status is null or :status = '' or status= :status) " +
            " LIMIT :page_size OFFSET :page",nativeQuery = true)
    List<BonusAndDiscipline> findAndCount(@Param("page") int page,
                              @Param("page_size")int page_size,
                              @Param("status") String status,
                              @Param("user_id") String user_id,
                                          @Param("type") String type
    );

    @Query(value = "Select count(*) from bonus_disciplines " +
            " WHERE TRUE " +
            "AND (:user_id is null or :user_id ='' or user_id =:user_id) " +
            "AND (:type is null or :type ='' or type =:type) " +
            " AND (:status is null or :status = '' or status= :status) ",nativeQuery = true)
    Long totalFilter(
            @Param("status") String status,
            @Param("user_id") String user_id,
            @Param("type") String type
    );

}