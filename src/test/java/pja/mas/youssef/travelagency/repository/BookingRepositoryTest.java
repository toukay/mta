package pja.mas.youssef.travelagency.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pja.mas.youssef.travelagency.model.Booking;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookingRepositoryTest {
    @Autowired
    private BookingRepository bookingRepository;

    @PersistenceContext
    private EntityManager entityManager;

    Booking b1;

    @BeforeEach
    public void initData() {
        b1 = Booking.builder()
                .bookingDate(java.time.LocalDateTime.now())
                .status(Booking.Status.DRAFT)
                .build();
    }

    @Test
    void testBookingRepository() {
        assertNotNull(bookingRepository);
    }

    @Test
    void testFetchAllBookings() {
        Iterable<Booking> bookings = bookingRepository.findAll();
        assertNotNull(bookings);
    }

    @Test
    void testSaveBooking() {
        Booking savedBooking = bookingRepository.save(b1);
        entityManager.flush();
        long count = bookingRepository.count();
        assertEquals(1, count);
    }
}