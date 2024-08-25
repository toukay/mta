package pja.mas.youssef.travelagency.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pja.mas.youssef.travelagency.model.Booking;
import pja.mas.youssef.travelagency.model.Tour;
import pja.mas.youssef.travelagency.model.customer.Customer;
import pja.mas.youssef.travelagency.model.customer.Individual;
import pja.mas.youssef.travelagency.model.employee.Agent;
import pja.mas.youssef.travelagency.repository.customer.CustomerRepository;
import pja.mas.youssef.travelagency.repository.employee.AgentRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookingRepositoryTest {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private TourRepository tourRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Booking booking1;
    private Booking booking2;
    private Customer customer;
    private Agent agent;
    private Tour tour;

    @BeforeEach
    public void initData() {
        // Create a customer
        customer = Individual.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .isVIP(false)
                .PESEL("12345678901")
                .build();
        customerRepository.save(customer);

        // Create an agent
        agent = Agent.builder()
                .firstName("Jane")
                .lastName("Smith")
                .branchAddress("123 Agent St")
                .name("Jane Smith")
                .specialization(Agent.Specialization.GENERAL)
                .build();
        agentRepository.save(agent);

        // Create a tour
        tour = Tour.builder()
                .destination("Paris")
                .pricePerSeat(500.0)
                .startDate(LocalDateTime.now().plusDays(30))
                .endDate(LocalDateTime.now().plusDays(37))
                .build();
        tourRepository.save(tour);

        // Create bookings
        booking1 = Booking.builder()
                .customer(customer)
                .agent(agent)
                .tour(tour)
                .bookingDate(LocalDateTime.now())
                .status(Booking.Status.CONFIRMED)
                .numberOfPeople(2)
                .emergencyContact("123-456-7890")
                .isTravelInsuranceIncluded(true)
                .isPrivateBusIncluded(false)
                .isMealsIncluded(true)
                .build();

        booking2 = Booking.builder()
                .customer(customer)
                .agent(agent)
                .tour(tour)
                .bookingDate(LocalDateTime.now())
                .status(Booking.Status.DRAFT)
                .numberOfPeople(1)
                .emergencyContact("098-765-4321")
                .isTravelInsuranceIncluded(false)
                .isPrivateBusIncluded(false)
                .isMealsIncluded(false)
                .build();
    }

    @Test
    void testBookingRepository() {
        assertNotNull(bookingRepository);
    }

    @Test
    void testSaveBooking() {
        Booking savedBooking = bookingRepository.save(booking1);
        entityManager.flush();

        Optional<Booking> fetchedBooking = bookingRepository.findById(savedBooking.getId());
        assertTrue(fetchedBooking.isPresent());
        assertEquals(booking1.getStatus(), fetchedBooking.get().getStatus());
        assertEquals(booking1.getNumberOfPeople(), fetchedBooking.get().getNumberOfPeople());
        assertEquals(booking1.getEmergencyContact(), fetchedBooking.get().getEmergencyContact());
    }

    @Test
    void testFetchAllBookings() {
        bookingRepository.save(booking1);
        bookingRepository.save(booking2);
        entityManager.flush();

        Iterable<Booking> bookings = bookingRepository.findAll();
        assertNotNull(bookings);
        int count = 0;
        for (Booking booking : bookings) {
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    void testUpdateBooking() {
        Booking savedBooking = bookingRepository.save(booking1);
        savedBooking.setStatus(Booking.Status.CANCELLED);
        savedBooking.setNumberOfPeople(3);
        bookingRepository.save(savedBooking);
        entityManager.flush();
        entityManager.clear();

        Optional<Booking> updatedBooking = bookingRepository.findById(savedBooking.getId());
        assertTrue(updatedBooking.isPresent());
        assertEquals(Booking.Status.CANCELLED, updatedBooking.get().getStatus());
        assertEquals(3, updatedBooking.get().getNumberOfPeople());
    }

    @Test
    void testDeleteBooking() {
        Booking savedBooking = bookingRepository.save(booking1);
        long initialCount = bookingRepository.count();
        bookingRepository.delete(savedBooking);
        entityManager.flush();
        long finalCount = bookingRepository.count();
        assertEquals(initialCount - 1, finalCount);
        Optional<Booking> deletedBooking = bookingRepository.findById(savedBooking.getId());
        assertFalse(deletedBooking.isPresent());
    }

    @Test
    void testBookingRelationships() {
        Booking savedBooking = bookingRepository.save(booking1);
        entityManager.flush();
        entityManager.clear();

        Booking fetchedBooking = bookingRepository.findById(savedBooking.getId()).orElseThrow();
        assertNotNull(fetchedBooking.getCustomer());
        assertNotNull(fetchedBooking.getAgent());
        assertNotNull(fetchedBooking.getTour());
        assertEquals(customer.getId(), fetchedBooking.getCustomer().getId());
        assertEquals(agent.getId(), fetchedBooking.getAgent().getId());
        assertEquals(tour.getId(), fetchedBooking.getTour().getId());
    }

    @Test
    void testGetTotalPrice() {
        Booking savedBooking = bookingRepository.save(booking1);
        entityManager.flush();
        entityManager.clear();

        Booking fetchedBooking = bookingRepository.findById(savedBooking.getId()).orElseThrow();
        double expectedPrice = 100 + 50 + 50 + (500.0 * 2); // BOOKING_FEE + EXTRA_SERVICE_FEE * 2 + tour price * numberOfPeople
        assertEquals(expectedPrice, fetchedBooking.getTotalPrice());
    }

    @Test
    void testPrivateBusInclusionForNonVIPCustomer() {
        assertThrows(IllegalArgumentException.class, () -> {
            Booking.builder()
                    .customer(customer)
                    .agent(agent)
                    .tour(tour)
                    .bookingDate(LocalDateTime.now())
                    .status(Booking.Status.CONFIRMED)
                    .numberOfPeople(2)
                    .emergencyContact("123-456-7890")
                    .isPrivateBusIncluded(true)
                    .build();
        });
    }
}