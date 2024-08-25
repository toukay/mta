package pja.mas.youssef.travelagency.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pja.mas.youssef.travelagency.model.TravelTicket;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TravelTicketRepositoryTest {
    @Autowired
    private TravelTicketRepository travelTicketRepository;

    @PersistenceContext
    private EntityManager entityManager;

    TravelTicket t1;

    @BeforeEach
    public void initData() {
        t1 = TravelTicket.builder()
                .ticketNumber("Ticket 1")
                .price(100.0)
                .build();
    }

    @Test
    public void testTravelTicketRepository(){
        assertNotNull(travelTicketRepository);
    }

    @Test
    public void testFetchAllTravelTickets(){
        Iterable<TravelTicket> travelTickets = travelTicketRepository.findAll();
        assertNotNull(travelTickets);
    }

    @Test
    public void testSaveTravelTicket(){
        TravelTicket savedTravelTicket = travelTicketRepository.save(t1);
        entityManager.flush();
        long count = travelTicketRepository.count();
        assertEquals(1, count);
    }

    @Test
    public void testFindByTicketNumber(){
        travelTicketRepository.save(t1);
        entityManager.flush();
        TravelTicket travelTicket = travelTicketRepository.findByTicketNumber("Ticket 1");
        assertEquals(t1, travelTicket);
    }
}