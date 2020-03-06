package dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
@Accessors(fluent = true, chain = true)
public class AccountPutRequest {
    private Long id;
    private String number;
    private BigDecimal balance;
}
