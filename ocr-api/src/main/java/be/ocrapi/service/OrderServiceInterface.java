package be.ocrapi.service;

import be.ocrapi.model.Order;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface OrderServiceInterface {
    Optional<Order> findById(Integer id);
    Page<Order> findAll(int page, int page_size);
    Order save(Order order);
    Order update(Order order);
    void delete(Order order);
}
