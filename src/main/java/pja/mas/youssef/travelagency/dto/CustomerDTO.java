package pja.mas.youssef.travelagency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class CustomerDTO {
    @JsonProperty("customer_id")
    private Long id;
    private String socialIdentifier;
    private String fullName;
    private String email;
    private Boolean isVIP;
}

