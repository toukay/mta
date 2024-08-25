package pja.mas.youssef.travelagency.model.employee;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class FullTimeEmployee extends EmployeeContract {
    private static final double MIN_SALARY = 40000;
    private static final double MAX_SALARY_INCREASE_RATE  = 0.25;
    private static Set<String> BENEFITS = new HashSet<>(
            Arrays.asList("Health Insurance", "Dental Insurance", "Paid Time Off", "Retirement Plan")
    );

    @NotNull(message = "Salary is required")
    @Min(value = (long)MIN_SALARY, message = "Salary must be greater than " + MIN_SALARY)
    private double monthlySalary;

    public static abstract class FullTimeEmployeeBuilder<C extends FullTimeEmployee, B extends FullTimeEmployeeBuilder<C, B>>
            extends EmployeeContractBuilder<C, B> {

        public B monthlySalary(double monthlySalary) {
            _validateSalary(this.monthlySalary, monthlySalary);
            this.monthlySalary = monthlySalary;
            return self();
        }
    }

    public void setMonthlySalary(double newSalary) {
        _validateSalary(this.monthlySalary, newSalary);
        this.monthlySalary = newSalary;
    }

    private static void _validateSalary(double currentSalary, double newSalary) {
        if (newSalary < MIN_SALARY) {
            throw new IllegalArgumentException("Salary cannot be less than the minimum salary of " + MIN_SALARY);
        }

        if (currentSalary != 0) {
            double changeRate = Math.abs(newSalary - currentSalary) / currentSalary;
            if (changeRate > MAX_SALARY_INCREASE_RATE) {
                throw new IllegalArgumentException("Salary change rate cannot exceed " + (MAX_SALARY_INCREASE_RATE * 100) + "%");
            }
        }
    }

    @Override
    public Double getBaseSalary() {
        return monthlySalary;
    }
}
