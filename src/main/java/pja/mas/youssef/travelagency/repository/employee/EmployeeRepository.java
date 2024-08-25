package pja.mas.youssef.travelagency.repository.employee;

import org.springframework.data.repository.CrudRepository;
import pja.mas.youssef.travelagency.model.employee.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
