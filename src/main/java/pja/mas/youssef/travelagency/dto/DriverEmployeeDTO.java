package pja.mas.youssef.travelagency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@Builder
public class DriverEmployeeDTO {
    @JsonProperty("employee_id")
    private Long id;
    private String firstName;
    private String lastName;
    private String branchAddress;
    private Integer yearsOfExperience;
}
