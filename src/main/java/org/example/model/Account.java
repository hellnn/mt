package org.example.model;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Long id;
    private String number;
    private BigDecimal balance;

    public Account(String number, BigDecimal balance) {
        this.number = number;
        this.balance = balance;
    }

}
