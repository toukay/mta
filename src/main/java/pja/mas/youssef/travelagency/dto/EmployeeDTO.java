package pja.mas.youssef.travelagency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import pja.mas.youssef.travelagency.model.employee.Agent;
import pja.mas.youssef.travelagency.model.employee.Guide;

import java.util.Set;

@Data
@Builder
public class EmployeeDTO {
    @JsonProperty("employee_id")
    private Long id;
    private String firstName;
    private String lastName;
    private String branchAddress;
}

