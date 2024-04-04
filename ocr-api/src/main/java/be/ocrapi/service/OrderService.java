package be.ocrapi.service;

import be.ocrapi.model.Order;
import be.ocrapi.repository.OrderRepository;
import be.ocrapi.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService implements OrderServiceInterface {
    @Autowired
    private OrderRepository orderRepository;

    private  Order setOrder(OrderRequest o) {
        Order newOrder = new Order();
        newOrder.setNode(o.getNode());
        newOrder.setDiscount(o.getDiscount());
        newOrder.setStatus(o.getStatus());
        newOrder.setReceiver_phone(o.getReceiver_phone());
        newOrder.setReceiver_email(o.getReceiver_email());
        newOrder.setReceiver_name(o.getReceiver_name());
        newOrder.setTotal_price(o.getTotal_price());
        newOrder.setTotal_discount(o.getTotal_discount());
        newOrder.setUser_id(o.getUser_id());
        return newOrder;
    }

    @Override
    public Order save(OrderRequest order) {
        Order o = setOrder(order);
        return orderRepository.save(o);
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public Page<Order> findAll(int page, int page_size) {
        Pageable pageable = PageRequest.of(page, page_size);
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
    }
}
