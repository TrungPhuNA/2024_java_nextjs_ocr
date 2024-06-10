package be.ocrapi.repository;

import be.ocrapi.model.Salary;
import be.ocrapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Integer> {

    @Query(value = "Select * from salaries " +
            " WHERE TRUE " +
            "AND (:user_id is null or :user_id ='' or user_id =:user_id) " +
            " AND (:status is null or :status = '' or status= :status) " +
            " LIMIT :page_size OFFSET :page",nativeQuery = true)
    List<Salary> findAndCount(@Param("page") int page,
                            @Param("page_size")int page_size,
                            @Param("status") String status,
                            @Param("user_id") String user_id
    );

    @Query(value = "Select count(*) from salaries " +
            " WHERE TRUE " +
            "AND (:user_id is null or :user_id ='' or user_id =:user_id) " +
            " AND (:status is null or :status = '' or status= :status) ",nativeQuery = true)
    Long totalFilter(
                            @Param("status") String status,
                            @Param("user_id") String user_id
    );

}