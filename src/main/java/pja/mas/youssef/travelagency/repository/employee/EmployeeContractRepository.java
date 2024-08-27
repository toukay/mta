package pja.mas.youssef.travelagency.repository.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import pja.mas.youssef.travelagency.model.employee.EmployeeContract;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeContractRepository extends JpaRepository<EmployeeContract, Long> {
//    public EmployeeContract findByEmployee(Employee employee);
//    public List<EmployeeContract> findByHireDate(LocalDate hireDate);
}
