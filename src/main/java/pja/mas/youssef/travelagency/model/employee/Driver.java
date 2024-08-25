package pja.mas.youssef.travelagency.model.employee;

import jakarta.persistence.Entity;
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
public class Driver extends Employee {
    private Integer yearsOfExperience;

    public void UpdateBusLocation() {
        // TODO implement here
    }
}
