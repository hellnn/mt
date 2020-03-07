package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class Account {
    private Long id;
    private String number;
    private BigDecimal balance;

    public Account(String number, BigDecimal balance) {
        this.number = number;
        this.balance = balance;
    }
}
