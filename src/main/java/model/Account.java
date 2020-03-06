package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class Account {
    private Long id;
    private String number;
    private BigDecimal balance;
}
