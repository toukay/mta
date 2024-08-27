package pja.mas.youssef.travelagency.repository.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import pja.mas.youssef.travelagency.model.employee.FullTimeEmployee;

public interface FullTimeEmployeeRepository extends JpaRepository<FullTimeEmployee, Long> {
}
