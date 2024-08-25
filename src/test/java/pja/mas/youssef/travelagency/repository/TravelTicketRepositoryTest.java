package pja.mas.youssef.travelagency.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pja.mas.youssef.travelagency.model.TravelTicket;
import pja.mas.youssef.travelagency.model.Booking;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TravelTicketRepositoryTest {
    @Autowired
    private TravelTicketRepository travelTicketRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private TravelTicket travelTicket1;
    private TravelTicket travelTicket2;
    private Booking booking;

    @BeforeEach
    public void initData() {
        // Create a dummy booking
        booking = Booking.builder()
                .bookingDate(LocalDateTime.now())
                .status(Booking.Status.CONFIRMED)
                .numberOfPeople(2)
                .emergencyContact("123-456-7890")
                .isTravelInsuranceIncluded(true)
                .isPrivateBusIncluded(false)
                .isMealsIncluded(true)
                .build();
        bookingRepository.save(booking);

        travelTicket1 = TravelTicket.builder()
                .ticketNumber("TICKET001")
                .price(100.0)
                .booking(booking)
                .build();

        travelTicket2 = TravelTicket.builder()
                .ticketNumber("TICKET002")
                .price(150.0)
                .booking(booking)
                .build();
    }

    @Test
    public void testTravelTicketRepository() {
        assertNotNull(travelTicketRepository);
    }

    @Test
    public void testSaveTravelTicket() {
        TravelTicket savedTicket = travelTicketRepository.save(travelTicket1);
        entityManager.flush();

        Optional<TravelTicket> fetchedTicket = travelTicketRepository.findById(savedTicket.getId());
        assertTrue(fetchedTicket.isPresent());
        assertEquals(travelTicket1.getTicketNumber(), fetchedTicket.get().getTicketNumber());
        assertEquals(travelTicket1.getPrice(), fetchedTicket.get().getPrice());
        assertEquals(booking.getId(), fetchedTicket.get().getBooking().getId());
    }

    @Test
    public void testFetchAllTravelTickets() {
        travelTicketRepository.save(travelTicket1);
        travelTicketRepository.save(travelTicket2);
        entityManager.flush();

        Iterable<TravelTicket> travelTickets = travelTicketRepository.findAll();
        assertNotNull(travelTickets);
        int count = 0;
        for (TravelTicket ticket : travelTickets) {
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    public void testFindByTicketNumber() {
        travelTicketRepository.save(travelTicket1);
        entityManager.flush();

        TravelTicket foundTicket = travelTicketRepository.findByTicketNumber("TICKET001");
        assertNotNull(foundTicket);
        assertEquals(travelTicket1.getTicketNumber(), foundTicket.getTicketNumber());
    }

    @Test
    public void testUpdateTravelTicket() {
        TravelTicket savedTicket = travelTicketRepository.save(travelTicket1);
        savedTicket.setPrice(120.0);
        travelTicketRepository.save(savedTicket);
        entityManager.flush();
        entityManager.clear();

        TravelTicket updatedTicket = travelTicketRepository.findById(savedTicket.getId()).orElseThrow();
        assertEquals(120.0, updatedTicket.getPrice());
    }

    @Test
    public void testDeleteTravelTicket() {
        TravelTicket savedTicket = travelTicketRepository.save(travelTicket1);
        long initialCount = travelTicketRepository.count();
        travelTicketRepository.delete(savedTicket);
        entityManager.flush();
        long finalCount = travelTicketRepository.count();
        assertEquals(initialCount - 1, finalCount);
        Optional<TravelTicket> deletedTicket = travelTicketRepository.findById(savedTicket.getId());
        assertFalse(deletedTicket.isPresent());
    }

    @Test
    public void testUniqueTicketNumber() {
        travelTicketRepository.save(travelTicket1);
        entityManager.flush();

        TravelTicket duplicateTicket = TravelTicket.builder()
                .ticketNumber("TICKET001")
                .price(200.0)
                .booking(booking)
                .build();

        assertThrows(Exception.class, () -> {
            travelTicketRepository.save(duplicateTicket);
            entityManager.flush();
        });
    }

    @Test
    public void testBookingRelationship() {
        TravelTicket savedTicket = travelTicketRepository.save(travelTicket1);
        entityManager.flush();
        entityManager.clear();

        TravelTicket fetchedTicket = travelTicketRepository.findById(savedTicket.getId()).orElseThrow();
        assertNotNull(fetchedTicket.getBooking());
        assertEquals(booking.getId(), fetchedTicket.getBooking().getId());
    }
}