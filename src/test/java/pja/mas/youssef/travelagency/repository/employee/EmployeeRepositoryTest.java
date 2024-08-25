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
import pja.mas.youssef.travelagency.model.employee.Employee;

import java.util.Optional;
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

    Agent agent;
    Guide guide;
    Driver driver;

    @BeforeEach
    public void initData() {
        agent = Agent.builder()
                .name("Agent 1")
                .firstName("John")
                .lastName("Lukas")
                .branchAddress("Branch 1")
                .specialization(Agent.Specialization.GENERAL)
                .build();
        guide = Guide.builder()
                .firstName("Jane")
                .lastName("Ray")
                .branchAddress("Branch 2")
                .languages(Set.of("English", "French"))
                .role(Guide.Role.HISTORIAN)
                .build();
        driver = Driver.builder()
                .firstName("Jack")
                .lastName("Doe")
                .branchAddress("Branch 3")
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
        agentRepository.save(agent);
        guideRepository.save(guide);
        driverRepository.save(driver);
        entityManager.flush();
        long count = employeeRepository.count();
        assertEquals(3, count);
    }

    @Test
    void testFindAgentById() {
        Agent savedAgent = agentRepository.save(agent);
        Optional<Agent> foundAgent = agentRepository.findById(savedAgent.getId());
        assertTrue(foundAgent.isPresent());
        assertEquals(agent.getName(), foundAgent.get().getName());
        assertEquals(agent.getSpecialization(), foundAgent.get().getSpecialization());
    }

    @Test
    void testFindGuideById() {
        Guide savedGuide = guideRepository.save(guide);
        Optional<Guide> foundGuide = guideRepository.findById(savedGuide.getId());
        assertTrue(foundGuide.isPresent());
        assertEquals(guide.getLanguages(), foundGuide.get().getLanguages());
        assertEquals(guide.getRole(), foundGuide.get().getRole());
    }

    @Test
    void testFindDriverById() {
        Driver savedDriver = driverRepository.save(driver);
        Optional<Driver> foundDriver = driverRepository.findById(savedDriver.getId());
        assertTrue(foundDriver.isPresent());
        assertEquals(driver.getYearsOfExperience(), foundDriver.get().getYearsOfExperience());
    }

    @Test
    void testDeleteEmployee() {
        Agent savedAgent = agentRepository.save(agent);
        long initialCount = employeeRepository.count();
        agentRepository.delete(savedAgent);
        entityManager.flush();
        long finalCount = employeeRepository.count();
        assertEquals(initialCount - 1, finalCount);
        Optional<Agent> deletedAgent = agentRepository.findById(savedAgent.getId());
        assertFalse(deletedAgent.isPresent());
    }

    @Test
    void testPolymorphicQuery() {
        agentRepository.save(agent);
        guideRepository.save(guide);
        driverRepository.save(driver);
        entityManager.flush();

        Iterable<Employee> allEmployees = employeeRepository.findAll();
        int count = 0;
        for (Employee employee : allEmployees) {
            count++;
            assertTrue(employee instanceof Agent || employee instanceof Guide || employee instanceof Driver);
        }
        assertEquals(3, count);
    }
}