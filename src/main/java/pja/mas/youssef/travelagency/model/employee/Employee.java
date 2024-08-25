package pja.mas.youssef.travelagency.model.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "employee", cascade = {CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private EmployeeContract contract;

    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
    private String lastName;

    @NotBlank(message = "Branch address is required")
    @Size(min = 3, max = 255, message = "Branch address must be between 3 and 255 characters")
    private String branchAddress;
}
