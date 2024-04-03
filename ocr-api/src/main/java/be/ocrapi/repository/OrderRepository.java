package be.ocrapi.repository;

import be.ocrapi.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//public interface OrderRepository extends PagingAndSortingRepository<Order, Integer> { }
public interface OrderRepository extends JpaRepository<Order, Integer> {


}