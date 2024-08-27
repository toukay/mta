package pja.mas.youssef.travelagency.repository.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import pja.mas.youssef.travelagency.model.employee.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
