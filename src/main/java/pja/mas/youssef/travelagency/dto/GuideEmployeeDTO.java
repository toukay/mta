package pja.mas.youssef.travelagency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import pja.mas.youssef.travelagency.model.employee.Guide;

import java.util.Set;

@Data
@Builder
public class GuideEmployeeDTO {
    @JsonProperty("employee_id")
    private Long id;
    private String firstName;
    private String lastName;
    private String branchAddress;
    private Set<String> languages;
    private Guide.Role role;
}
