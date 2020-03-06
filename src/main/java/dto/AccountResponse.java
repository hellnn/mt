package dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Setter
@Getter
@ToString
@Accessors(fluent = true, chain = true)
@XmlRootElement(name = "AccountResponse")
public class AccountResponse {
    private Long id;
    private String number;
    private BigDecimal balance;
}
