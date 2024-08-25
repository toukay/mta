package pja.mas.youssef.travelagency.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pja.mas.youssef.travelagency.model.Tour;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TourRepositoryTest {
    @Autowired
    private TourRepository tourRepository;

    @PersistenceContext
    private EntityManager entityManager;

    Tour tour1;
    Tour tour2;

    @BeforeEach
    public void initData() {
        tour1 = Tour.builder()
                .destination("Paris")
                .pricePerSeat(100.0)
                .startDate(LocalDateTime.of(2022, 1, 1, 12, 0))
                .endDate(LocalDateTime.of(2022, 1, 2, 12, 0))
                .build();

        tour2 = Tour.builder()
                .destination("London")
                .pricePerSeat(150.0)
                .startDate(LocalDateTime.of(2022, 2, 1, 12, 0))
                .endDate(LocalDateTime.of(2022, 2, 3, 12, 0))
                .build();
    }

    @Test
    void testTourRepository() {
        assertNotNull(tourRepository);
    }

    @Test
    void testFetchAllTours() {
        tourRepository.save(tour1);
        tourRepository.save(tour2);
        Iterable<Tour> tours = tourRepository.findAll();
        assertNotNull(tours);
        int count = 0;
        for (Tour tour : tours) {
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    void testSaveTour() {
        Tour savedTour = tourRepository.save(tour1);
        entityManager.flush();
        Optional<Tour> fetchedTour = tourRepository.findById(savedTour.getId());
        assertTrue(fetchedTour.isPresent());
        assertEquals(tour1.getDestination(), fetchedTour.get().getDestination());
        assertEquals(tour1.getPricePerSeat(), fetchedTour.get().getPricePerSeat());
        assertEquals(tour1.getStartDate(), fetchedTour.get().getStartDate());
        assertEquals(tour1.getEndDate(), fetchedTour.get().getEndDate());
    }

    @Test
    void testFindByDestination() {
        tourRepository.save(tour1);
        tourRepository.save(tour2);
        Iterable<Tour> parisTours = tourRepository.findByDestination("Paris");
        assertNotNull(parisTours);
        int count = 0;
        for (Tour tour : parisTours) {
            count++;
            assertEquals("Paris", tour.getDestination());
        }
        assertEquals(1, count);
    }

    @Test
    void testFindById() {
        Tour savedTour = tourRepository.save(tour1);
        Optional<Tour> foundTour = tourRepository.findById(savedTour.getId());
        assertTrue(foundTour.isPresent());
        assertEquals(savedTour.getId(), foundTour.get().getId());
    }

    @Test
    void testDeleteTour() {
        Tour savedTour = tourRepository.save(tour1);
        long initialCount = tourRepository.count();
        tourRepository.delete(savedTour);
        long finalCount = tourRepository.count();
        assertEquals(initialCount - 1, finalCount);
        Optional<Tour> deletedTour = tourRepository.findById(savedTour.getId());
        assertFalse(deletedTour.isPresent());
    }
}