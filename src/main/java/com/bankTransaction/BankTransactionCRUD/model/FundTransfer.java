package com.bankTransaction.BankTransactionCRUD.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class FundTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transferId;

    @Column(name = "sender_account_id", nullable = false)
    private Long senderAccountId;

    @Column(name = "receiver_account_id", nullable = false)
    private Long receiverAccountId;

    @Column(nullable = false)
    private BigDecimal amount;

    private String status = "pending";  // "pending", "completed", etc.

    private LocalDateTime initiatedAt = LocalDateTime.now();
    private LocalDateTime completedAt;
}
