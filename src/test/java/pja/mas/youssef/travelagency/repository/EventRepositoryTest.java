package pja.mas.youssef.travelagency.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pja.mas.youssef.travelagency.model.Event;
import pja.mas.youssef.travelagency.model.Tour;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EventRepositoryTest {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TourRepository tourRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Event event1;
    private Tour tour;

    @BeforeEach
    public void initData() {
        tour = Tour.builder()
                .destination("Paris")
                .pricePerSeat(1000.0)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(7))
                .build();
        tourRepository.save(tour);

        event1 = Event.builder()
                .name("Event 1")
                .tour(tour)
                .categories(Set.of(Event.Category.CULTURE, Event.Category.SOCIAL))
                .attractions(Set.of("Attraction 1", "Attraction 2"))
                .guests(Set.of("Guest 1", "Guest 2"))
                .build();
    }

    @Test
    void testEventRepository() {
        assertNotNull(eventRepository);
    }

    @Test
    void testFetchAllEvents() {
        eventRepository.save(event1);
        Iterable<Event> events = eventRepository.findAll();
        assertNotNull(events);
        assertTrue(((List<Event>) events).size() > 0);
    }

    @Test
    void testSaveEvent() {
        Event savedEvent = eventRepository.save(event1);
        entityManager.flush();
        Optional<Event> fetchedEvent = eventRepository.findById(savedEvent.getId());
        assertTrue(fetchedEvent.isPresent());
        assertEquals(event1.getName(), fetchedEvent.get().getName());
    }

    @Test
    void testFindByName() {
        eventRepository.save(event1);
        List<Event> events = eventRepository.findByName("Event 1");
        assertFalse(events.isEmpty());
        assertEquals(1, events.size());
        assertEquals(event1.getName(), events.get(0).getName());
    }

    @Test
    void testDeleteEvent() {
        Event savedEvent = eventRepository.save(event1);
        eventRepository.delete(savedEvent);
        Optional<Event> deletedEvent = eventRepository.findById(savedEvent.getId());
        assertFalse(deletedEvent.isPresent());
    }

    @Test
    void testGetActivities() {
        Event savedEvent = eventRepository.save(event1);
        Set<String> activities = savedEvent.getActivities();
        assertEquals(4, activities.size());
        assertTrue(activities.contains("Attraction 1"));
        assertTrue(activities.contains("Attraction 2"));
        assertTrue(activities.contains("Guest 1"));
        assertTrue(activities.contains("Guest 2"));
    }
}
