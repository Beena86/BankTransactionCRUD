package com.bankTransaction.BankTransactionCRUD.service;

import com.bankTransaction.BankTransactionCRUD.ResourceNotFoundException;
import com.bankTransaction.BankTransactionCRUD.model.Account;
import com.bankTransaction.BankTransactionCRUD.model.FundTransfer;
import com.bankTransaction.BankTransactionCRUD.model.Transaction;
import com.bankTransaction.BankTransactionCRUD.repository.FundTransferRepository;
import com.bankTransaction.BankTransactionCRUD.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final FundTransferRepository fundTransferRepository;
    private final RestTemplate restTemplate;

    @Value("${account.service.url}")
    private String accountServiceUrl;

    //private final String accountServiceUrl = "http://localhost:8088/api/accounts/";
    @Override
    public Transaction createTransaction(Transaction transaction) {
        String accountUrl = accountServiceUrl + "/accounts/" + transaction.getAccountId();
        Account account = restTemplate.getForObject(accountUrl, Account.class);

        if (account == null) {
            throw new ResourceNotFoundException("Account with ID " + transaction.getAccountId() + " not found");
        }

        // Perform validation (e.g., sufficient balance for a debit transaction)
        if ("debit".equalsIgnoreCase(transaction.getTransactionType()) &&
                account.getBalance().compareTo(transaction.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance for the transaction");
        }

        // Update account balance
        if ("debit".equalsIgnoreCase(transaction.getTransactionType())) {
            account.setBalance(account.getBalance().subtract(transaction.getAmount()));
        } else if ("credit".equalsIgnoreCase(transaction.getTransactionType())) {
            account.setBalance(account.getBalance().add(transaction.getAmount()));
        }

        // Save the updated account details using RestTemplate
        restTemplate.put(accountUrl, account);

        // Save the transaction
        return transactionRepository.save(transaction);
    }

    @Override
    public FundTransfer createFundTransfer(FundTransfer fundTransfer) {
        String senderUrl = accountServiceUrl + "/accounts/" + fundTransfer.getSenderAccountId();
        String receiverUrl = accountServiceUrl + "/accounts/" + fundTransfer.getReceiverAccountId();

        // Use RestTemplate to fetch sender and receiver account details
        Account senderAccount = restTemplate.getForObject(senderUrl, Account.class);
        Account receiverAccount = restTemplate.getForObject(receiverUrl, Account.class);

        // Validate that accounts exist and balance is sufficient
        if (senderAccount == null || receiverAccount == null) {
            throw new ResourceNotFoundException("Sender or receiver account not found");
        }
        if (senderAccount.getBalance().compareTo(fundTransfer.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance in sender's account");
        }

        // Deduct amount from sender and add to receiver
        senderAccount.setBalance(senderAccount.getBalance().subtract(fundTransfer.getAmount()));
        receiverAccount.setBalance(receiverAccount.getBalance().add(fundTransfer.getAmount()));

        // Update accounts in the account microservice
        restTemplate.put(senderUrl, senderAccount);
        restTemplate.put(receiverUrl, receiverAccount);

        // Save the fund transfer
        fundTransfer.setStatus("completed");
        fundTransfer.setCompletedAt(LocalDateTime.now());
        return fundTransferRepository.save(fundTransfer);
    }
}
