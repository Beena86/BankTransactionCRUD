package com.bankTransaction.BankTransactionCRUD.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private String transactionType;  // "credit" or "debit"

    @Column(nullable = false)
    private BigDecimal amount =BigDecimal.ZERO;;

    private String description;


    private LocalDateTime createdAt = LocalDateTime.now();
}
