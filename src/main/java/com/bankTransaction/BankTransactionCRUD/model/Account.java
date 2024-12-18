package com.bankTransaction.BankTransactionCRUD.model;
import jakarta.persistence.Column;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Account {

    private Long accountId;
    private Long userId;
    private String accountNumber;
    private String accountType;
    @NotNull
    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;;
    private String currency;
}
