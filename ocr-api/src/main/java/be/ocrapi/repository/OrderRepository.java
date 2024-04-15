package be.ocrapi.repository;

import be.ocrapi.model.Order;
import be.ocrapi.model.User;
import be.ocrapi.response.OrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
//    @Query(value = "SELECT count(*) as total FROM orders o where true and o.status=:status",nativeQuery = true)
//    int countOrderByStatus(@Param("status") int status);

        Integer countOrderByStatus(int status);

        @Query(value = "select sum(o.total_price) " +
                "FROM orders o where o.created_at is not null and DATE(o.created_at) >= :from_date " +
                "and Date(o.created_at) <= :to_date ",nativeQuery = true)
        Integer sumTotalPriceByDate(@Param("from_date") String from_date, @Param("to_date") String to_date);


        @Query(value = "SELECT sum(total_price) as total FROM orders o where true and o.status=:status",nativeQuery = true)
        Integer sumPriceOrderByStatus(@Param("status") int status);

}