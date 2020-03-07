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
@XmlRootElement
@Accessors(chain = true)
public class AccountTransferRequest {
    private String srcNumber;
    private String destNumber;
    private BigDecimal sum;
}
