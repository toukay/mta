package pja.mas.youssef.travelagency.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pja.mas.youssef.travelagency.model.Bus;
import pja.mas.youssef.travelagency.model.Location;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BusRepositoryTest {
    @Autowired
    private BusRepository busRepository;

    @PersistenceContext
    private EntityManager entityManager;

    Bus b1;

    @BeforeEach
    public void initData() {
        b1 = Bus.builder()
                .model("Nissan")
                .capacity(50)
                .location(new Location(52.2297, 21.0122))
                .build();
    }

    @Test
    void testBusRepository() {
        assertNotNull(busRepository);
    }

    @Test
    void testFetchAllBuses() {
        Iterable<Bus> buses = busRepository.findAll();
        assertNotNull(buses);
    }

    @Test
    void testSaveBus() {
        Bus savedBus = busRepository.save(b1);
        entityManager.flush();
        long count = busRepository.count();
        assertEquals(1, count);
    }
}