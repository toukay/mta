package pja.mas.youssef.travelagency.repository.employee;

import org.springframework.data.repository.CrudRepository;
import pja.mas.youssef.travelagency.model.employee.Team;

import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Long> {
    public List<Team> findByName(String name);
}
