package be.ocrapi.repository;

import be.ocrapi.model.Salary;
import be.ocrapi.model.TimeAttendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<TimeAttendance, Integer> {

    @Query(value = "SELECT * FROM time_attendance p  WHERE  " +
            "(:from_date = '' OR :from_date IS NULL OR p.check_in >= :from_date ) AND " +
            "(:to_date = '' OR :to_date IS NULL OR p.check_in <= :to_date ) AND " +
            "(:type = '' OR :type IS NULL OR p.type=:type) AND " +
            "(:user_id = '' OR :user_id IS NULL OR p.user_id=:user_id ) AND " +
            "(:status = '' OR :status IS NULL OR p.status = :status) " +
            " LIMIT :page_size OFFSET :page" ,nativeQuery = true)
    List<TimeAttendance> getList(
            @Param("from_date") String from_date,
            @Param("to_date") String to_date,
            @Param("type") String type,
            @Param("user_id") String user_id,
            @Param("status") String status,
            int page, int page_size);


    @Query(value = "Select count(*) from time_attendance p " +
            " WHERE TRUE AND " +
            "(:from_date = '' OR :from_date IS NULL OR p.check_in >= :from_date ) AND " +
            "(:to_date = '' OR :to_date IS NULL OR p.check_in <= :to_date ) AND " +
            "(:type_data = '' OR :type_data IS NULL OR p.type=:type_data) AND " +
            "(:user_id = '' OR :user_id IS NULL OR p.user_id=:user_id ) AND " +
            "(:status = '' OR :status IS NULL OR p.status = :status) ",nativeQuery = true)
    Long totalFilter(
            @Param("from_date") String from_date,
            @Param("to_date") String to_date,
            @Param("type_data") String type_data,
            @Param("user_id") String user_id,
            @Param("status") String status
    );
}