package pja.mas.youssef.travelagency.repository.employee;

import org.springframework.data.repository.CrudRepository;
import pja.mas.youssef.travelagency.model.employee.FullTimeEmployee;

public interface FullTimeEmployeeRepository extends CrudRepository<FullTimeEmployee, Long> {
}
