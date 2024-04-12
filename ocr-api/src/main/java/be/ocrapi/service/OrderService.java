package be.ocrapi.service;

import be.ocrapi.model.Order;
import be.ocrapi.model.Transaction;
import be.ocrapi.repository.OrderRepository;
import be.ocrapi.request.OrderRequest;
import be.ocrapi.request.TransactionRequest;
import be.ocrapi.response.OrderResponse;
import be.ocrapi.response.StatisticResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class OrderService implements OrderServiceInterface {
    @Autowired
    private OrderRepository orderRepository;

//    private String genCode() {
//
//        return UUID.randomUUID().toString();
//    }

    private String  makeCode(int length) {
        String result = "";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int charactersLength = characters.length();
        for (int i = 0; i < length; i++) {
            result += characters.charAt((int) (Math.random() * charactersLength));
        }
        return result;
    }

    public Order setOrder(OrderRequest o, Order newData) {
        if(newData == null) {
            newData = new Order();
            newData.setCreated_at(new Date());
        }
        System.out.println(newData.toString());
        newData.setNode(o.getNode());
        newData.setPayment_type(o.getPayment_type());
        newData.setCategory_id(o.getCategory_id());
        newData.setName(o.getName());
       if(o.getCode() == "" || o.getCode() == null) {
           newData.setCode(makeCode(15));
       }
        newData.setStatus(o.getStatus());
        newData.setReceiver_phone(o.getReceiver_phone());
        newData.setReceiver_email(o.getReceiver_email());
        newData.setReceiver_name(o.getReceiver_name());
        newData.setTotal_price(o.getTotal_price());
        newData.setTotal_discount(o.getTotal_discount());
        newData.setUser_id(o.getUser_id());
        newData.setUpdated_at(new Date());
        return newData;
    }

    @Override
    public Order save(OrderRequest order) {
        Order o = setOrder(order, null);
        return orderRepository.save(o);
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public Page<Order> findAll(int page, int page_size) {
        Pageable pageable = PageRequest.of(page, page_size, Sort.by(Sort.Direction.DESC, "id"));
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order update(int id,OrderRequest data) {
        Order d = orderRepository.getById(id);
        if(d != null) {
            d = setOrder(data, d);
            return orderRepository.save(d);
        }
        throw new RuntimeException("Cập nhật thất bại");
    }

    @Override
    public StatisticResponse getStatistic() {
        List<OrderResponse> total = new ArrayList<>();
        List<OrderResponse> price = new ArrayList<>();
        Integer total_order = 0;
        Integer total_price = 0;
        for (int i = 1; i <= 4 ; i++) {
            var totalStatus = orderRepository.countOrderByStatus(i);
            if(totalStatus == null) {
                totalStatus = 0;
            }
            total_order += totalStatus;
            System.err.println("count totalStatus=======> "+ i + "----> "+totalStatus);
            OrderResponse totalItem = new OrderResponse(i, totalStatus, 0);
            var totalPrice = orderRepository.sumPriceOrderByStatus(i);
            if(totalPrice == null) {
                totalPrice = 0 ;
            }
            total_price += totalPrice;
            System.err.println("count price=======> "+ i + "----> "+totalPrice);
            OrderResponse priceByStatus = new OrderResponse(i, 0, totalPrice);

            total.add(totalItem);
            price.add(priceByStatus);
        }
        StatisticResponse statistic = new StatisticResponse(total,  total_order, total_price, price);
        return statistic;
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
    }
}
