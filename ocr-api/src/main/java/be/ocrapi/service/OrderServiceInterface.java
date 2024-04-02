package be.ocrapi.service;

import be.ocrapi.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderServiceInterface {
    Optional<Order> findOrder(Integer id);
    List<Order> findAll();
    Order save(Order order);
    Order update(Order order);
    void delete(Order order);
}
