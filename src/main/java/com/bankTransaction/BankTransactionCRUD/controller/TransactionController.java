package com.bankTransaction.BankTransactionCRUD.controller;

import com.bankTransaction.BankTransactionCRUD.model.FundTransfer;
import com.bankTransaction.BankTransactionCRUD.model.Transaction;
import com.bankTransaction.BankTransactionCRUD.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.ok(createdTransaction);
    }
    @PostMapping("/fund-transfer")
    public ResponseEntity<FundTransfer> createFundTransfer(@RequestBody FundTransfer fundTransfer) {
        FundTransfer createdFundTransfer = transactionService.createFundTransfer(fundTransfer);
        return ResponseEntity.ok(createdFundTransfer);
    }
}
