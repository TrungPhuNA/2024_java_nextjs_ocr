package be.ocrapi.controller;

import be.ocrapi.model.Order;
import be.ocrapi.service.OrderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    @Autowired
    private OrderServiceInterface orderService;

    @GetMapping("{id}")
    public ResponseEntity<Order> findCategory(@PathVariable Integer id) {
        Optional<Order> userOptional = orderService.findOrder(id);
        return userOptional.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @PostMapping
    public ResponseEntity<Order> save(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.save(order));
    }

    @PutMapping
    public ResponseEntity<Order> update(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.update(order));
    }

    @DeleteMapping
    public void delete(@RequestBody Order order) {
        orderService.delete(order);
    }
}
