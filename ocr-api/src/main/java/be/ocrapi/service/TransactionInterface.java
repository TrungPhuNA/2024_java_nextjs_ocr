package be.ocrapi.service;

import be.ocrapi.model.Transaction;
import be.ocrapi.request.TransactionRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface TransactionInterface {
    Optional<Transaction> findById(Integer id);
    Page<Transaction> findAll(int page, int page_size);
    Transaction save(TransactionRequest data);

    List<Transaction> saveAll(List<TransactionRequest> data);
    Transaction update(int id, TransactionRequest data);
    void deleteByOrderIds(int orderIds);
    void delete(Transaction order);
}
