package pja.mas.youssef.travelagency.repository.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import pja.mas.youssef.travelagency.model.employee.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long> {
}
