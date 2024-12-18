package com.bankTransaction.BankTransactionCRUD.service;

import com.bankTransaction.BankTransactionCRUD.model.FundTransfer;
import com.bankTransaction.BankTransactionCRUD.model.Transaction;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);
    FundTransfer createFundTransfer(FundTransfer fundTransfer);

}
