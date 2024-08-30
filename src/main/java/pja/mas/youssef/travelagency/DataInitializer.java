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
import pja.mas.youssef.travelagency.model.employee.Guide;
import pja.mas.youssef.travelagency.repository.*;
import pja.mas.youssef.travelagency.repository.customer.CompanyRepository;
import pja.mas.youssef.travelagency.repository.customer.IndividualRepository;
import pja.mas.youssef.travelagency.repository.employee.AgentRepository;
import pja.mas.youssef.travelagency.repository.employee.GuideRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(DataInitializer.class);

    private final IndividualRepository individualRepository;
    private final CompanyRepository companyRepository;
    private final AgentRepository agentRepository;
    private final GuideRepository guideRepository;
    private final TourRepository tourRepository;
    private final BookingRepository bookingRepository;
    private final AccommodationRepository accommodationRepository;
    private final TravelTicketRepository travelTicketRepository;
    private final EventRepository eventRepository;

    private final Random random = new Random();

    @EventListener
    public void atStart(ContextRefreshedEvent event) {
        LOG.info("DataInitializer.atStart");
        if (isDatabaseEmpty()) {
            initData();
        } else {
            LOG.info("Database is not empty, skipping data initialization");
        }
    }

    private boolean isDatabaseEmpty() {
        return individualRepository.count() == 0 &&
                companyRepository.count() == 0 &&
                agentRepository.count() == 0 &&
                guideRepository.count() == 0 &&
                tourRepository.count() == 0 &&
                bookingRepository.count() == 0 &&
                accommodationRepository.count() == 0 &&
                travelTicketRepository.count() == 0 &&
                eventRepository.count() == 0;
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

        // Create Guides
        List<Guide> guides = List.of(
                createGuide("James", "Smith", Set.of("English", "French"), Guide.Role.LEADER),
                createGuide("Emma", "Johnson", Set.of("English", "French"), Guide.Role.LEADER),
                createGuide("Liam", "Brown", Set.of("English", "Spanish"), Guide.Role.HISTORIAN),
                createGuide("Sophia", "Garcia", Set.of("English", "Spanish", "Italian"), Guide.Role.TRANSLATOR),
                createGuide("Noah", "Martinez", Set.of("English", "Portuguese"), Guide.Role.ENTERTAINER),
                createGuide("Olivia", "Taylor", Set.of("English", "German"), Guide.Role.LEADER),
                createGuide("Ethan", "Anderson", Set.of("English", "Mandarin"), Guide.Role.TRANSLATOR),
                createGuide("Ava", "Thomas", Set.of("English", "Arabic"), Guide.Role.HISTORIAN),
                createGuide("Mason", "Jackson", Set.of("English", "Russian"), Guide.Role.ENTERTAINER)
        );
        guideRepository.saveAll(guides);

        // Create Tours with Guides
        List<Tour> tours = List.of(
                createTourWithGuides("Paris Getaway", 1000.0, LocalDateTime.now().plusMonths(1), LocalDateTime.now().plusMonths(1).plusDays(7), List.of(guides.get(0), guides.get(2))),
                createTourWithGuides("Tokyo Adventure", 1500.0, LocalDateTime.now().plusMonths(2), LocalDateTime.now().plusMonths(2).plusDays(10), List.of(guides.get(1), guides.get(3), guides.get(4))),
                createTourWithGuides("New York City Tour", 1200.0, LocalDateTime.now().plusMonths(3), LocalDateTime.now().plusMonths(3).plusDays(5), List.of(guides.get(5), guides.get(6))),
                createTourWithGuides("African Safari", 2000.0, LocalDateTime.now().plusMonths(4), LocalDateTime.now().plusMonths(4).plusDays(14), List.of(guides.get(0), guides.get(7), guides.get(8))),
                createTourWithGuides("Australian Outback", 1800.0, LocalDateTime.now().plusMonths(5), LocalDateTime.now().plusMonths(5).plusDays(12), List.of(guides.get(1), guides.get(3), guides.get(4))),
                createTourWithGuides("Mediterranean Cruise", 2200.0, LocalDateTime.now().plusMonths(6), LocalDateTime.now().plusMonths(6).plusDays(15), List.of(guides.get(2), guides.get(5), guides.get(6))),
                createTourWithGuides("South American Adventure", 1900.0, LocalDateTime.now().plusMonths(7), LocalDateTime.now().plusMonths(7).plusDays(13), List.of(guides.get(0), guides.get(7), guides.get(8)))
        );
        tourRepository.saveAll(tours);

        // Create Events and associate with Tours
        for (Tour tour : tours) {
            createEventsForTour(tour);
        }

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

    private Guide createGuide(String firstName, String lastName, Set<String> languages, Guide.Role role) {
        return Guide.builder()
                .firstName(firstName)
                .lastName(lastName)
                .branchAddress("123 Guide St")
                .languages(languages)
                .role(role)
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
                .emergencyContact("Jack - 123-456-" + random.nextInt(1000, 9999))
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

    private void createEventsForTour(Tour tour) {
        List<Event> events = new ArrayList<>();
        int numberOfEvents = random.nextInt(3, 6);  // Create 3-5 events per tour

        for (int i = 0; i < numberOfEvents; i++) {
            Event event = createEvent(tour);
            events.add(event);
        }

        eventRepository.saveAll(events);
        tour.getEvents().addAll(events);
        tourRepository.save(tour);
    }

    private Event createEvent(Tour tour) {
        String[] eventNames = {"City Tour", "Museum Visit", "Local Cuisine Tasting", "Historical Site Exploration", "Cultural Performance", "Nature Hike", "Wine Tasting", "Art Gallery Tour"};
        String[] attractions = {"Eiffel Tower", "Louvre Museum", "Notre-Dame Cathedral", "Tokyo Tower", "Sensoji Temple", "Statue of Liberty", "Central Park", "Serengeti National Park", "Sydney Opera House"};
        String[] guests = {"Local Guide", "Historian", "Chef", "Artist", "Musician", "Naturalist", "Sommelier"};

        Set<Event.Category> categories = new HashSet<>();
        categories.add(Event.Category.values()[random.nextInt(Event.Category.values().length)]);
        if (random.nextBoolean()) {
            categories.add(Event.Category.values()[random.nextInt(Event.Category.values().length)]);
        }

        Set<String> eventAttractions = new HashSet<>();
        eventAttractions.add(attractions[random.nextInt(attractions.length)]);
        if (random.nextBoolean()) {
            eventAttractions.add(attractions[random.nextInt(attractions.length)]);
        }

        Set<String> eventGuests = new HashSet<>();
        eventGuests.add(guests[random.nextInt(guests.length)]);
        if (random.nextBoolean()) {
            eventGuests.add(guests[random.nextInt(guests.length)]);
        }

        return Event.builder()
                .tour(tour)
                .name(eventNames[random.nextInt(eventNames.length)])
                .categories(categories)
                .attractions(eventAttractions)
                .guests(eventGuests)
                .build();
    }

    private Tour createTourWithGuides(String destination, Double pricePerSeat, LocalDateTime startDate, LocalDateTime endDate, List<Guide> availableGuides) {
        Tour tour = Tour.builder()
                .destination(destination)
                .pricePerSeat(pricePerSeat)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        // Assign guides to the tour
        Map<Guide.Role, TourGuide> tourGuides = new EnumMap<>(Guide.Role.class);
        for (Guide.Role role : Guide.Role.values()) {
            Guide selectedGuide = getRandomGuideByRole(availableGuides, role);
            if (selectedGuide != null) {
                TourGuide tourGuide = new TourGuide(tour, selectedGuide, role);
                tourGuides.put(role, tourGuide);
            }
        }
        tour.setTourGuides(tourGuides);

        return tour;
    }

    private Guide getRandomGuideByRole(List<Guide> guides, Guide.Role role) {
        List<Guide> guidesWithRole = guides.stream()
                .filter(guide -> guide.getRole() == role)
                .toList();
        return guidesWithRole.isEmpty() ? null : guidesWithRole.get(random.nextInt(guidesWithRole.size()));
    }
}