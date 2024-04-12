package be.ocrapi.repository;

import be.ocrapi.model.Order;
import be.ocrapi.model.User;
import be.ocrapi.response.OrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
//    @Query(value = "SELECT count(*) as total FROM orders o where true and o.status=:status",nativeQuery = true)
//    int countOrderByStatus(@Param("status") int status);

    Integer countOrderByStatus(int status);

//    Integer (int status);


        @Query(value = "SELECT sum(total_price) as total FROM orders o where true and o.status=:status",nativeQuery = true)
        Integer sumPriceOrderByStatus(@Param("status") int status);

}