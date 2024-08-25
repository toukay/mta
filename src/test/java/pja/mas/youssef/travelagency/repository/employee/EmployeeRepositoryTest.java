package pja.mas.youssef.travelagency.repository.employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pja.mas.youssef.travelagency.model.employee.Agent;
import pja.mas.youssef.travelagency.model.employee.Driver;
import pja.mas.youssef.travelagency.model.employee.Guide;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private GuideRepository guideRepository;
    @Autowired
    private DriverRepository driverRepository;

    @PersistenceContext
    private EntityManager entityManager;

    Agent a1;
    Guide g1;
    Driver d1;

    @BeforeEach
    public void initData() {
        a1 = Agent.builder()
                .firstName("John")
                .lastName("Lukas")
                .branchAddress("Branch 1")
                .specialization(Agent.Specialization.GENERAL)
                .build();
        g1 = Guide.builder()
                .firstName("Jane")
                .lastName("Ray")
                .languages(Set.of("English", "French"))
                .role(Guide.Role.HISTORIAN)
                .build();
        d1 = Driver.builder()
                .firstName("Jack")
                .lastName("Doe")
                .yearsOfExperience(5)
                .build();
    }

    @Test
    void testEmployeeDependencies(){
        assertNotNull(employeeRepository);
        assertNotNull(agentRepository);
        assertNotNull(guideRepository);
        assertNotNull(driverRepository);
    }

    @Test
    void testSaveAllEmployees(){
        agentRepository.save(a1);
        guideRepository.save(g1);
        driverRepository.save(d1);
        entityManager.flush();
        long count = employeeRepository.count();
        assertEquals(3, count);
    }
}