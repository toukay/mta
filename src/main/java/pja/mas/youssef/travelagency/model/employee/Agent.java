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

    public static abstract class AgentBuilder<C extends Agent, B extends AgentBuilder<C, B>>
            extends EmployeeBuilder<C, B> {

        @Singular
        private Set<Team> teams = new HashSet<>();

        @Singular
        private Set<Team> managedTeams = new HashSet<>();

        public B team(Team team) {
            if (team == null) {
                throw new IllegalArgumentException("Team cannot be null");
            }
            this.teams.add(team);
            return self();
        }

        public B teams(Set<Team> teams) {
            if (teams == null) {
                throw new IllegalArgumentException("Teams cannot be null");
            }
            this.teams.addAll(teams);
            return self();
        }

        public B managedTeam(Team team) {
            if (team == null) {
                throw new IllegalArgumentException("Managed team cannot be null");
            }
            if (this.teams == null || !this.teams.contains(team)) {
                throw new IllegalArgumentException("Agent must be a member of the team " + team.getName() + " before becoming a manager");
            }
            this.managedTeams.add(team);
            return self();
        }

        public B managedTeams(Set<Team> managedTeams) {
            if (managedTeams == null) {
                throw new IllegalArgumentException("Managed teams cannot be null");
            }
            for (Team team : managedTeams) {
                managedTeam(team);
            }
            return self();
        }
    }

    public void addManagedTeam(Team team) throws Exception {
        if (team == null) {
            throw new Exception("Managed team cannot be null");
        }
        if (!teams.contains(team)) {
            throw new Exception("Agent must be a member of the team before becoming a manager");
        }
        if (!managedTeams.contains(team)) {
            managedTeams.add(team);
            team.getManagers().add(this);
        }
    }

    public void removeTeam(Team team) {
        if (team == null) {
            throw new IllegalArgumentException("Team cannot be null");
        }
        if (teams.contains(team)) {
            teams.remove(team);
            team.getAgents().remove(this);
            if (managedTeams.contains(team)) {
                managedTeams.remove(team);
                team.getManagers().remove(this);
            }
        }
    }

    public void removeManagedTeam(Team team) {
        if (team == null) {
            throw new IllegalArgumentException("Managed team cannot be null");
        }
        if (managedTeams.contains(team)) {
            managedTeams.remove(team);
            team.getManagers().remove(this);
        }
    }
}