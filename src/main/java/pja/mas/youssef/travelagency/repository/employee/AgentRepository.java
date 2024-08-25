package pja.mas.youssef.travelagency.repository.employee;

import org.springframework.data.repository.CrudRepository;
import pja.mas.youssef.travelagency.model.employee.Agent;

public interface AgentRepository extends CrudRepository<Agent, Long> {
}
