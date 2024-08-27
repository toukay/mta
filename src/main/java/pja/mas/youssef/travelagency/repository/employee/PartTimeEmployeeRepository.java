package pja.mas.youssef.travelagency.repository.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import pja.mas.youssef.travelagency.model.employee.PartTimeEmployee;

public interface PartTimeEmployeeRepository extends JpaRepository<PartTimeEmployee, Long> {
}
