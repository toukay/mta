package pja.mas.youssef.travelagency.model.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class EmployeeContract {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(optional = false, cascade = {CascadeType.REMOVE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Employee employee;

    @NotNull(message = "Hire date is required")
    private LocalDate hireDate;

    public abstract Double getBaseSalary();
}
