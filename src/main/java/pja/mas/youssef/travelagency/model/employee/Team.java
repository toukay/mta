package pja.mas.youssef.travelagency.model.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "team_manager",
            uniqueConstraints = @UniqueConstraint(columnNames = {"team_id", "manager_id"}),
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "manager_id")
    )
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Agent> managers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "team_agent",
            uniqueConstraints = @UniqueConstraint(columnNames = {"team_id", "agent_id"}),
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "agent_id")
    )
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Agent> agents = new HashSet<>();

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    public static class TeamBuilder {
        private Set<Agent> agents = new HashSet<>();

        private Set<Agent> managers = new HashSet<>();

        public TeamBuilder agent(Agent agent) {
            if (agent == null) {
                throw new IllegalArgumentException("Agent cannot be null");
            }
            this.agents.add(agent);
            return this;
        }

        public TeamBuilder agents(Agent agent) {
            if (agent == null) {
                throw new IllegalArgumentException("Agent cannot be null");
            }
            this.agents.add(agent);
            return this;
        }

        public TeamBuilder agents(Set<Agent> agents) {
            if (agents == null) {
                throw new IllegalArgumentException("Agents cannot be null");
            }
            this.agents.addAll(agents);
            return this;
        }

        public TeamBuilder manager(Agent manager) {
            if (manager == null) {
                throw new IllegalArgumentException("Manager cannot be null");
            }
            if (this.agents == null || !this.agents.contains(manager)) {
                throw new IllegalArgumentException("Agent must be a member of the team before becoming a manager");
            }
            this.managers.add(manager);
            return this;
        }

        public TeamBuilder managers(Set<Agent> managers) {
            if (managers == null) {
                throw new IllegalArgumentException("Managers cannot be null");
            }
            for (Agent manager : managers) {
                manager(manager);
            }
            return this;
        }

    }

    public void addAgent(Agent agent) {
        if (agent == null) {
            throw new IllegalArgumentException("Agent cannot be null");
        }
        if (!agents.contains(agent)) {
            agents.add(agent);
            agent.getTeams().add(this);
        }
    }

    public void addManager(Agent agent) {
        if (agent == null) {
            throw new IllegalArgumentException("Agent cannot be null");
        }
        if (!agents.contains(agent)) {
            throw new IllegalArgumentException("Agent must be a member of the team before becoming a manager");
        }
        if (!managers.contains(agent)) {
            managers.add(agent);
            agent.getManagedTeams().add(this);
        }
    }

    public void removeAgent(Agent agent) {
        if (agent == null) {
            throw new IllegalArgumentException("Agent cannot be null");
        }
        if (agents.remove(agent)) {
            agent.getTeams().remove(this);
            if (managers.contains(agent)) {
                managers.remove(agent);
                agent.getManagedTeams().remove(this);
            }
        }
    }

    public void removeManager(Agent agent) {
        if (agent == null) {
            throw new IllegalArgumentException("Agent cannot be null");
        }
        if (managers.remove(agent)) {
            agent.getManagedTeams().remove(this);
        }
    }
}
