package pja.mas.youssef.travelagency.repository.employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pja.mas.youssef.travelagency.model.employee.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeContractRepositoryTest {
    @Autowired
    private EmployeeContractRepository employeeContractRepository;
    @Autowired
    private FullTimeEmployeeRepository fullTimeEmployeeRepository;
    @Autowired
    private PartTimeEmployeeRepository partTimeEmployeeRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Employee employee1;
    private Employee employee2;
    private FullTimeEmployee fullTimeContract;
    private PartTimeEmployee partTimeContract;

    @BeforeEach
    void setUp() {
        employee1 = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .branchAddress("123 Main St")
                .build();
        employee2 = Employee.builder()
                .firstName("Jane")
                .lastName("Doe")
                .branchAddress("456 Elm St")
                .build();
        employeeRepository.saveAll(java.util.List.of(employee1, employee2));

        fullTimeContract = FullTimeEmployee.builder()
                .employee(employee1)
                .hireDate(LocalDate.of(2019, 1, 1))
                .monthlySalary(50000.0)
                .build();
        partTimeContract = PartTimeEmployee.builder()
                .employee(employee2)
                .hireDate(LocalDate.of(2020, 6, 24))
                .hourlyWage(25.0)
                .build();
    }

    @Test
    void testEmployeeContractDependencies() {
        assertNotNull(employeeContractRepository);
        assertNotNull(fullTimeEmployeeRepository);
        assertNotNull(partTimeEmployeeRepository);
    }

    @Test
    void testSaveAllEmployeeContracts() {
        fullTimeEmployeeRepository.save(fullTimeContract);
        partTimeEmployeeRepository.save(partTimeContract);
        entityManager.flush();
        long count = employeeContractRepository.count();
        assertEquals(2, count);
    }

    @Test
    void testFindFullTimeEmployeeContract() {
        FullTimeEmployee savedContract = fullTimeEmployeeRepository.save(fullTimeContract);
        entityManager.flush();
        Optional<EmployeeContract> foundContract = employeeContractRepository.findById(savedContract.getId());
        assertTrue(foundContract.isPresent());
        assertTrue(foundContract.get() instanceof FullTimeEmployee);
        assertEquals(fullTimeContract.getMonthlySalary(), ((FullTimeEmployee) foundContract.get()).getMonthlySalary());
    }

    @Test
    void testFindPartTimeEmployeeContract() {
        PartTimeEmployee savedContract = partTimeEmployeeRepository.save(partTimeContract);
        entityManager.flush();
        Optional<EmployeeContract> foundContract = employeeContractRepository.findById(savedContract.getId());
        assertTrue(foundContract.isPresent());
        assertTrue(foundContract.get() instanceof PartTimeEmployee);
        assertEquals(partTimeContract.getHourlyWage(), ((PartTimeEmployee) foundContract.get()).getHourlyWage());
    }

    @Test
    void testUpdateFullTimeEmployeeContract() {
        FullTimeEmployee savedContract = fullTimeEmployeeRepository.save(fullTimeContract);
        savedContract.setMonthlySalary(55000.0);
        fullTimeEmployeeRepository.save(savedContract);
        entityManager.flush();
        entityManager.clear();

        Optional<EmployeeContract> updatedContract = employeeContractRepository.findById(savedContract.getId());
        assertTrue(updatedContract.isPresent());
        assertTrue(updatedContract.get() instanceof FullTimeEmployee);
        assertEquals(55000.0, ((FullTimeEmployee) updatedContract.get()).getMonthlySalary());
    }

    @Test
    void testDeleteEmployeeContract() {
        FullTimeEmployee savedContract = fullTimeEmployeeRepository.save(fullTimeContract);
        long initialCount = employeeContractRepository.count();
        employeeContractRepository.delete(savedContract);
        entityManager.flush();
        long finalCount = employeeContractRepository.count();
        assertEquals(initialCount - 1, finalCount);
    }

    @Test
    void testEmployeeContractRelationship() {
        FullTimeEmployee savedContract = fullTimeEmployeeRepository.save(fullTimeContract);
        entityManager.flush();
        entityManager.clear();

        Employee fetchedEmployee = employeeRepository.findById(employee1.getId()).orElseThrow();
        assertNotNull(fetchedEmployee.getContract());
        assertEquals(savedContract.getId(), fetchedEmployee.getContract().getId());
    }

    @Test
    void testGetBaseSalary() {
        FullTimeEmployee savedFullTimeContract = fullTimeEmployeeRepository.save(fullTimeContract);
        PartTimeEmployee savedPartTimeContract = partTimeEmployeeRepository.save(partTimeContract);
        entityManager.flush();
        entityManager.clear();

        EmployeeContract fetchedFullTimeContract = employeeContractRepository.findById(savedFullTimeContract.getId()).orElseThrow();
        EmployeeContract fetchedPartTimeContract = employeeContractRepository.findById(savedPartTimeContract.getId()).orElseThrow();

        assertEquals(50000.0, fetchedFullTimeContract.getBaseSalary());
        assertEquals(25.0 * 20 * 4, fetchedPartTimeContract.getBaseSalary()); // Assuming 4 weeks per month
    }

    @Test
    void testInvalidSalaryChange() {
        FullTimeEmployee savedContract = fullTimeEmployeeRepository.save(fullTimeContract);
        assertThrows(IllegalArgumentException.class, () -> {
            savedContract.setMonthlySalary(30000.0); // This should throw an exception due to minimum salary constraint
        });
        assertThrows(IllegalArgumentException.class, () -> {
            savedContract.setMonthlySalary(70000.0); // This should throw an exception due to maximum salary increase rate
        });
    }
}