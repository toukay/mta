package pja.mas.youssef.travelagency.repository.employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pja.mas.youssef.travelagency.model.employee.Employee;
import pja.mas.youssef.travelagency.model.employee.FullTimeEmployee;
import pja.mas.youssef.travelagency.model.employee.PartTimeEmployee;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeContractRepositoryTest {
    @Autowired
    private EmployeeContractRepository employeeContractRepository;
    @Autowired
    private FullTimeEmployeeRepository fullTimeEmployeeRepository;
    @Autowired
    private PartTimeEmployeeRepository partTimeEmployeeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    Employee e1;
    Employee e2;

    FullTimeEmployee fullTimeEmployee;
    PartTimeEmployee partTimeEmployee;

    @BeforeEach
    void setUp() {
        e1 = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .build();
        e2 = Employee.builder()
                .firstName("Jane")
                .lastName("Doe")
                .build();

        fullTimeEmployee = FullTimeEmployee.builder()
                .hireDate(java.time.LocalDate.of(2019, 1, 1))
                .build();
        partTimeEmployee = PartTimeEmployee.builder()
                .hireDate(java.time.LocalDate.of(2020, 6, 24))
                .build();
    }

    @Test
    void testEmployeeContractDependencies(){
        assertNotNull(employeeContractRepository);
        assertNotNull(fullTimeEmployeeRepository);
        assertNotNull(partTimeEmployeeRepository);
    }

    @Test
    void testSaveAllEmployeeContracts(){
        fullTimeEmployeeRepository.save(fullTimeEmployee);
        partTimeEmployeeRepository.save(partTimeEmployee);
        entityManager.flush();
        long count = employeeContractRepository.count();
        assertEquals(2, count);
    }
}