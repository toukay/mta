package pja.mas.youssef.travelagency.model.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pja.mas.youssef.travelagency.model.Booking;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Agent extends Employee {
    @OneToMany(mappedBy = "agent", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Booking> bookings = new HashSet<>();

    @ManyToMany(mappedBy = "agents")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Team> teams = new HashSet<>();

    @ManyToMany(mappedBy = "managers")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Team> managedTeams = new HashSet<>();

    @NotNull(message = "Specialization is required")
    private Specialization specialization;

    public enum Specialization {
        GENERAL, CORPORATE, LUXURY
    }
}
