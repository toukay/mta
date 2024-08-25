package pja.mas.youssef.travelagency.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pja.mas.youssef.travelagency.model.Event;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EventRepositoryTest {
    @Autowired
    private EventRepository eventRepository;

    @PersistenceContext
    private EntityManager entityManager;

    Event e1;

    @BeforeEach
    public void initData() {
        e1 = Event.builder()
                .name("Event 1")
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
        Iterable<Event> events = eventRepository.findAll();
        assertNotNull(events);
    }

    @Test
    void testSaveEvent() {
        Event savedEvent = eventRepository.save(e1);
        entityManager.flush();
        long count = eventRepository.count();
        assertEquals(1, count);
    }
}