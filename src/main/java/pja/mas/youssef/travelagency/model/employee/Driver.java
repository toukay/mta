package pja.mas.youssef.travelagency.model.employee;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pja.mas.youssef.travelagency.model.Bus;
import pja.mas.youssef.travelagency.model.Location;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Driver extends Employee {
    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Bus> buses = new HashSet<>();

    private Integer yearsOfExperience;

    public void UpdateBusLocation(Bus bus, Location location) {
        if (!buses.contains(bus)) {
            throw new IllegalArgumentException("This driver is not assigned to this bus");
        }

        bus.setLocation(location);
    }
}
