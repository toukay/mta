package pja.mas.youssef.travelagency.model.employee;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class PartTimeEmployee extends EmployeeContract {
    private static final int MIN_HOURS_PER_WEEK = 30;
    private static final double MIN_HOURLY_WAGE = 20.0;
    private static final double MAX_WAGE_INCREASE_RATE = 0.25;

    @NotNull(message = "Hourly wage is required")
    @Min(value = (long)MIN_HOURLY_WAGE, message = "Hourly wage must be greater than " + MIN_HOURLY_WAGE)
    private Double hourlyWage;

    public static abstract class PartTimeEmployeeBuilder<C extends PartTimeEmployee, B extends PartTimeEmployeeBuilder<C, B>>
            extends EmployeeContractBuilder<C, B> {

        public B hourlyWage(Double hourlyWage) {
            _validateHourlyWage(this.hourlyWage, hourlyWage);
            this.hourlyWage = hourlyWage;
            return self();
        }
    }

    public void setHourlyWage(Double newHourlyWage) {
        _validateHourlyWage(this.hourlyWage, newHourlyWage);
        this.hourlyWage = newHourlyWage;
    }

    private static void _validateHourlyWage(Double currentWage, Double newWage) {
        if (newWage < MIN_HOURLY_WAGE) {
            throw new IllegalArgumentException("Hourly wage cannot be less than the minimum wage of " + MIN_HOURLY_WAGE);
        }

        if (currentWage != null && currentWage != 0) {
            double changeRate = Math.abs(newWage - currentWage) / currentWage;
            if (changeRate > MAX_WAGE_INCREASE_RATE) {
                throw new IllegalArgumentException("Hourly wage change rate cannot exceed " + (MAX_WAGE_INCREASE_RATE * 100) + "%");
            }
        }
    }

    @Override
    public Double getBaseSalary() {
        return hourlyWage * MIN_HOURS_PER_WEEK;
    }
}