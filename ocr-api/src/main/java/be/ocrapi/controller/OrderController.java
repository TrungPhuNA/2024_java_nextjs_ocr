package be.ocrapi.controller;

import be.ocrapi.common.BaseResponse;
import be.ocrapi.common.BusinessErrorCode;
import be.ocrapi.common.BusinessException;
import be.ocrapi.model.Order;
import be.ocrapi.model.Transaction;
import be.ocrapi.request.OrderRequest;
import be.ocrapi.request.TransactionRequest;
import be.ocrapi.service.OrderServiceInterface;
import be.ocrapi.service.TransactionInterface;
import be.ocrapi.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderServiceInterface orderService;
    @Autowired
    private TransactionInterface transactionService;

    @GetMapping("statistic")
    public BaseResponse<?> statistic(
            @RequestParam(name = "month", required = false) String month
    ) {
        try {

            return BaseResponse.ofSucceeded(orderService.getStatistic(month));
        } catch (Exception e) {
            log.debug("[ORDER CONTROLLER]------>error statistic", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[ORDER CONTROLLER]------>statistic", error);
            return BaseResponse.ofFailed(error);
        }
    }
    @GetMapping("show/{id}")
    public BaseResponse<?> findOne(@PathVariable Integer id) {
        try {
            return BaseResponse.ofSucceeded(orderService.findById(id));
        } catch (Exception e) {
            log.debug("[ORDER CONTROLLER]------>error findOne", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[ORDER CONTROLLER]------>findOne", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @GetMapping("list")
    public BaseResponse<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") String page,
            @RequestParam(name = "page_size", required = false, defaultValue = "20") String page_size
    ) {
        try {
            int number_page = 0;
            if(Integer.parseInt(page) > 1) {
                number_page = Integer.parseInt(page) - 1;
            }
            var response = orderService.findAll(number_page, Integer.parseInt(page_size));
            return BaseResponse.ofSucceeded(response);
        } catch (Exception e) {
            log.debug("[ORDER CONTROLLER]------>error list", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[ORDER CONTROLLER]------>list", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @PostMapping("store")
    public BaseResponse<?> save(@RequestBody OrderRequest order) {
        try {
            var newOrder = orderService.save(order);
            if(newOrder != null) {
                List<Transaction> t = new ArrayList<Transaction>();
                for(TransactionRequest item :order.getTransactions()) {
                    item.setOrder_id(newOrder.getId());
                    Transaction newT = transactionService.save(item);
                    t.add(newT);
                }
                newOrder.setTransactions(t);
            }
            return BaseResponse.ofSucceeded(newOrder);
        } catch (Exception e) {
            System.out.print("[ORDER CONTROLLER]------>error create-----> " +e.getMessage() );
            System.out.print("[ORDER CONTROLLER]------>error create-----> " +e.getCause() );
            log.debug("[ORDER CONTROLLER]------>error create", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[ORDER CONTROLLER]------>create", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @PutMapping("update/{id}")
    public BaseResponse<?> update(@PathVariable Integer id, @RequestBody OrderRequest order) {
        try {
            var newOrder = orderService.update(id, order);
            if(newOrder != null) {
                System.out.println(id);
                transactionService.deleteByOrderIds(id);
                List<Transaction> t = new ArrayList<Transaction>();
                for(TransactionRequest item :order.getTransactions()) {
                    item.setOrder_id(newOrder.getId());
                    Transaction newT = transactionService.save(item);
                    t.add(newT);
                }
                newOrder.setTransactions(t);
            }
            return BaseResponse.ofSucceeded(newOrder);
        } catch (Exception e) {
            log.debug("[ORDER CONTROLLER]------>error update", e);
            String message = e.getMessage();
            var error = new BusinessException(new BusinessErrorCode(400, message, message, 400));
            log.error("[ORDER CONTROLLER]------>update", error);
            return BaseResponse.ofFailed(error);
        }
    }

    @DeleteMapping("delete/{id}")
    public void delete(@RequestBody Order order) {
        orderService.delete(order);
    }
}
