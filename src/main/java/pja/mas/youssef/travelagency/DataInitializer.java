package pja.mas.youssef.travelagency;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pja.mas.youssef.travelagency.model.*;
import pja.mas.youssef.travelagency.model.customer.Company;
import pja.mas.youssef.travelagency.model.customer.Individual;
import pja.mas.youssef.travelagency.model.employee.Agent;
import pja.mas.youssef.travelagency.repository.*;
import pja.mas.youssef.travelagency.repository.customer.CompanyRepository;
import pja.mas.youssef.travelagency.repository.customer.IndividualRepository;
import pja.mas.youssef.travelagency.repository.employee.AgentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(DataInitializer.class);

    private final IndividualRepository individualRepository;
    private final CompanyRepository companyRepository;
    private final AgentRepository agentRepository;
    private final TourRepository tourRepository;
    private final BookingRepository bookingRepository;
    private final AccommodationRepository accommodationRepository;
    private final TravelTicketRepository travelTicketRepository;

    private final Random random = new Random();

    @EventListener
    public void atStart(ContextRefreshedEvent event) {
        LOG.info("DataInitializer.atStart");
        initData();
    }

    private void initData() {
        LOG.info("Initializing data");

        // Create Individuals
        List<Individual> individuals = List.of(
                createIndividual("John", "Doe", "john.doe@example.com", true, "12345678901"),
                createIndividual("Jane", "Smith", "jane.smith@example.com", false, "23456789012"),
                createIndividual("Alice", "Johnson", "alice.johnson@example.com", true, "34567890123")
        );
        individualRepository.saveAll(individuals);

        // Create Companies
        List<Company> companies = List.of(
                createCompany("ABC Corp", "abc@corp.com", true, "1234567890"),
                createCompany("XYZ Ltd", "xyz@ltd.com", false, "0987654321")
        );
        companyRepository.saveAll(companies);

        // Create Agents
        List<Agent> agents = List.of(
                createAgent("Mike", "Wilson", "123 Agent St", "Mike Wilson", Agent.Specialization.GENERAL),
                createAgent("Sarah", "Brown", "456 Agent Ave", "Sarah Brown", Agent.Specialization.CORPORATE),
                createAgent("David", "Lee", "789 Agent Blvd", "David Lee", Agent.Specialization.LUXURY)
        );
        agentRepository.saveAll(agents);

        // Create Tours
        List<Tour> tours = List.of(
                createTour("Paris Getaway", 1000.0, LocalDateTime.now().plusMonths(1), LocalDateTime.now().plusMonths(1).plusDays(7)),
                createTour("Tokyo Adventure", 1500.0, LocalDateTime.now().plusMonths(2), LocalDateTime.now().plusMonths(2).plusDays(10)),
                createTour("New York City Tour", 1200.0, LocalDateTime.now().plusMonths(3), LocalDateTime.now().plusMonths(3).plusDays(5))
        );
        tourRepository.saveAll(tours);

        // Create Accommodations
        List<Accommodation> accommodations = List.of(
                createAccommodation("Luxury Hotel", "123 Luxury St, Paris", 200.0),
                createAccommodation("Budget Inn", "456 Budget Ave, Tokyo", 80.0),
                createAccommodation("Midrange Suites", "789 Midrange Blvd, New York", 150.0)
        );
        accommodationRepository.saveAll(accommodations);

        // Create Bookings
        for (int i = 0; i < 10; i++) {
            Booking booking = createBooking(
                    getRandomElement(individuals),
                    getRandomElement(agents),
                    getRandomElement(tours),
                    getRandomElement(accommodations)
            );
            Booking savedBooking = bookingRepository.save(booking);

            // Create Travel Tickets for each booking
            for (int j = 0; j < booking.getNumberOfPeople(); j++) {
                TravelTicket ticket = createTravelTicket(savedBooking);
                travelTicketRepository.save(ticket);
            }
        }

        LOG.info("Data initialization completed");
    }

    private Individual createIndividual(String firstName, String lastName, String email, boolean isVIP, String pesel) {
        return Individual.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .isVIP(isVIP)
                .PESEL(pesel)
                .build();
    }

    private Company createCompany(String name, String email, boolean isVIP, String nip) {
        return Company.builder()
                .name(name)
                .email(email)
                .isVIP(isVIP)
                .NIP(nip)
                .build();
    }

    private Agent createAgent(String firstName, String lastName, String branchAddress, String name, Agent.Specialization specialization) {
        return Agent.builder()
                .firstName(firstName)
                .lastName(lastName)
                .branchAddress(branchAddress)
                .name(name)
                .specialization(specialization)
                .build();
    }

    private Tour createTour(String destination, Double pricePerSeat, LocalDateTime startDate, LocalDateTime endDate) {
        return Tour.builder()
                .destination(destination)
                .pricePerSeat(pricePerSeat)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    private Accommodation createAccommodation(String name, String address, Double pricePerNight) {
        return Accommodation.builder()
                .name(name)
                .address(address)
                .pricePerNight(pricePerNight)
                .build();
    }

    private Booking createBooking(Object customer, Agent agent, Tour tour, Accommodation accommodation) {
        Booking booking = Booking.builder()
                .customer(customer instanceof Individual ? (Individual) customer : (Company) customer)
                .agent(agent)
                .tour(tour)
                .bookingDate(LocalDateTime.now())
                .status(Booking.Status.values()[random.nextInt(Booking.Status.values().length)])
                .numberOfPeople(random.nextInt(1, 6))
                .emergencyContact("123-456-" + random.nextInt(1000, 9999))
                .isTravelInsuranceIncluded(random.nextBoolean())
                .isPrivateBusIncluded(customer instanceof Individual ? ((Individual) customer).getIsVIP() && random.nextBoolean() : ((Company) customer).getIsVIP() && random.nextBoolean())
                .isMealsIncluded(random.nextBoolean())
                .build();

        BookingAccommodation bookingAccommodation = BookingAccommodation.builder()
                .booking(booking)
                .accommodation(accommodation)
                .checkInDate(LocalDate.from(tour.getStartDate()))
                .checkOutDate(LocalDate.from(tour.getEndDate()))
                .build();

        booking.getBookingAccommodations().add(bookingAccommodation);

        return booking;
    }

    private TravelTicket createTravelTicket(Booking booking) {
        return TravelTicket.builder()
                .booking(booking)
                .ticketNumber("TKT-" + random.nextInt(100000, 999999))
                .price(booking.getTour().getPricePerSeat())
                .build();
    }

    private <T> T getRandomElement(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }
}