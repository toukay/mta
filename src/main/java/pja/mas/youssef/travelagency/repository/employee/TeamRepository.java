package pja.mas.youssef.travelagency.repository.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import pja.mas.youssef.travelagency.model.employee.Team;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    public List<Team> findByName(String name);
}
