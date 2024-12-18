package com.bankTransaction.BankTransactionCRUD.repository;

import com.bankTransaction.BankTransactionCRUD.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    //List<Transaction> findByAccountId(Long accountId);
}
