package com.bankTransaction.BankTransactionCRUD.repository;

import com.bankTransaction.BankTransactionCRUD.model.FundTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface FundTransferRepository extends JpaRepository<FundTransfer, Long> {
}
