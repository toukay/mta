package pja.mas.youssef.travelagency.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class CompanyCustomerDTO {
    private String NIP;
    private String companyName;
}
