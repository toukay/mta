package pja.mas.youssef.travelagency.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class IndividualCustomerDTO extends CustomerDTO {
    private String PESEL;
    private String firstName;
    private String lastName;
}
