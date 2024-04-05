package be.ocrapi.service;

import be.ocrapi.model.Transaction;
import be.ocrapi.repository.TransactionRepository;
import be.ocrapi.request.TransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class TransactionService implements TransactionInterface{

    @Autowired
    private TransactionRepository repository;

    public Transaction setData(TransactionRequest d, Transaction newData) {
        if(newData == null) {
            newData = new Transaction();
        }
        newData.setName(d.getName());
        newData.setDiscount(d.getDiscount());
        newData.setPrice(d.getPrice());
        newData.setQuantity(d.getQuantity());
        newData.setStatus(d.getStatus());
        newData.setOrder_id(d.getOrder_id());
        newData.setTotal_price(d.getTotal_price());
        return newData;
    }
    @Override
    public void deleteByOrderIds(int orderIds) {
        repository.deleteTransactionByOrder_id(orderIds);
    }

    @Override
    public Optional<Transaction> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Page<Transaction> findAll(int page, int page_size) {
        Pageable pageable = PageRequest.of(page, page_size);
        return repository.findAll(pageable);
    }

    @Override
    public Transaction save(TransactionRequest data) {
        var newData = setData(data, null);
        return repository.save(newData);
    }

    @Override
    public List<Transaction> saveAll(List<TransactionRequest> data) {
        List<Transaction> d = new ArrayList<>();
        for(TransactionRequest item :data) {
            Transaction newData = setData(item, null);
            d.add(newData);
        }
        return repository.saveAll(d);
    }

    @Override
    public Transaction update(int id, TransactionRequest data) {
        Transaction d = repository.getById(id);
        if(d != null) {
            d = setData(data, d);
            return repository.save(d);
        }
        throw new RuntimeException("Cập nhật thất bại");
    }

    @Override
    public void delete(Transaction order) {

    }
}
