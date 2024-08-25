package pja.mas.youssef.travelagency.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pja.mas.youssef.travelagency.model.Bus;
import pja.mas.youssef.travelagency.model.Location;
import pja.mas.youssef.travelagency.model.employee.Driver;
import pja.mas.youssef.travelagency.repository.employee.DriverRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BusRepositoryTest {
    @Autowired
    private BusRepository busRepository;

    @Autowired
    private DriverRepository driverRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Bus bus1;
    private Bus bus2;
    private Driver driver;

    @BeforeEach
    public void initData() {
        driver = Driver.builder()
                .firstName("John")
                .lastName("Doe")
                .branchAddress("123 Main St, City, Country")  // Add this line
                .yearsOfExperience(5)
                .build();
        driverRepository.save(driver);

        bus1 = Bus.builder()
                .model("Nissan")
                .capacity(50)
                .location(new Location(52.2297, 21.0122))
                .driver(driver)
                .build();

        bus2 = Bus.builder()
                .model("Mercedes")
                .capacity(40)
                .location(new Location(51.5074, -0.1278))
                .driver(driver)
                .build();
    }

    @Test
    void testBusRepository() {
        assertNotNull(busRepository);
    }

    @Test
    void testFetchAllBuses() {
        busRepository.save(bus1);
        busRepository.save(bus2);
        Iterable<Bus> buses = busRepository.findAll();
        assertNotNull(buses);
        int count = 0;
        for (Bus bus : buses) {
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    void testSaveBus() {
        Bus savedBus = busRepository.save(bus1);
        entityManager.flush();
        Optional<Bus> fetchedBus = busRepository.findById(savedBus.getId());
        assertTrue(fetchedBus.isPresent());
        assertEquals(bus1.getModel(), fetchedBus.get().getModel());
        assertEquals(bus1.getCapacity(), fetchedBus.get().getCapacity());
        assertEquals(bus1.getLocation(), fetchedBus.get().getLocation());
        assertEquals(bus1.getDriver().getId(), fetchedBus.get().getDriver().getId());
    }

    @Test
    void testFindById() {
        Bus savedBus = busRepository.save(bus1);
        entityManager.flush();
        Optional<Bus> foundBus = busRepository.findById(savedBus.getId());
        assertTrue(foundBus.isPresent());
        assertEquals(savedBus.getId(), foundBus.get().getId());
    }

    @Test
    void testDeleteBus() {
        Bus savedBus = busRepository.save(bus1);
        long initialCount = busRepository.count();
        busRepository.delete(savedBus);
        entityManager.flush();
        long finalCount = busRepository.count();
        assertEquals(initialCount - 1, finalCount);
        Optional<Bus> deletedBus = busRepository.findById(savedBus.getId());
        assertFalse(deletedBus.isPresent());
    }

    @Test
    void testBusDriverRelationship() {
        Bus savedBus = busRepository.save(bus1);
        entityManager.flush();
        entityManager.clear();

        Bus fetchedBus = busRepository.findById(savedBus.getId()).orElseThrow();
        assertNotNull(fetchedBus.getDriver());
        assertEquals(driver.getId(), fetchedBus.getDriver().getId());
        assertEquals("123 Main St, City, Country", fetchedBus.getDriver().getBranchAddress());
    }
}