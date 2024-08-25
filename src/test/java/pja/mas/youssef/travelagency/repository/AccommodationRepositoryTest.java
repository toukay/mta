package pja.mas.youssef.travelagency.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pja.mas.youssef.travelagency.model.Accommodation;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccommodationRepositoryTest {
    @Autowired
    private AccommodationRepository accommodationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    Accommodation a1;

    @BeforeEach
    public void initData() {
        a1 = Accommodation.builder()
                .name("Accommodation 1")
                .address("Accommodation 1 address")
                .pricePerNight(100.0)
                .build();
    }

    @Test
    void testAccommodationRepository() {
        assertNotNull(accommodationRepository);
    }

    @Test
    void testFetchAllAccommodations() {
        Iterable<Accommodation> accommodations = accommodationRepository.findAll();
        assertNotNull(accommodations);
    }

    @Test
    void testSaveAccommodation() {
        Accommodation savedAccommodation = accommodationRepository.save(a1);
        entityManager.flush();
        long count = accommodationRepository.count();
        assertEquals(1, count);
    }

    @Test
    void testFindByName() {
        accommodationRepository.save(a1);
        entityManager.flush();
        Accommodation accommodation = accommodationRepository.findByName("Accommodation 1").get(0);
        assertEquals(a1, accommodation);
    }
}