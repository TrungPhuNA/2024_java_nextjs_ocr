package be.ocrapi.repository;

import be.ocrapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Modifying
    @Query(value = "DELETE FROM transactions WHERE TRUE AND order_id = :id",nativeQuery = true)
    @Transactional
    void deleteTransactionByOrder_id(@Param("id")int id);
}
