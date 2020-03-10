package org.example.dto;

import lombok.*;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Setter
@Getter
@ToString
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AccountRequest {
    private String number;
    private BigDecimal balance;
}
