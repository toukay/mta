package pja.mas.youssef.travelagency.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pja.mas.youssef.travelagency.model.Accommodation;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccommodationRepositoryTest {
    @Autowired
    private AccommodationRepository accommodationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    Accommodation a1, a2, a3;

    @BeforeEach
    public void initData() {
        accommodationRepository.deleteAll();

        a1 = Accommodation.builder()
                .name("Luxury Hotel")
                .address("123 Main St, City")
                .pricePerNight(200.0)
                .build();

        a2 = Accommodation.builder()
                .name("Budget Inn")
                .address("456 Side St, Town")
                .pricePerNight(50.0)
                .build();

        a3 = Accommodation.builder()
                .name("Cozy Cottage")
                .address("789 Country Rd, Village")
                .pricePerNight(100.0)
                .build();

        accommodationRepository.saveAll(List.of(a1, a2, a3));
        entityManager.flush();
    }

    @Test
    void testAccommodationRepository() {
        assertNotNull(accommodationRepository);
    }

    @Test
    void testFetchAllAccommodations() {
        Iterable<Accommodation> accommodations = accommodationRepository.findAll();
        assertNotNull(accommodations);
        assertEquals(3, ((List<Accommodation>) accommodations).size());
    }

    @Test
    void testSaveAccommodation() {
        Accommodation newAccommodation = Accommodation.builder()
                .name("New Hotel")
                .address("New Address")
                .pricePerNight(150.0)
                .build();
        Accommodation savedAccommodation = accommodationRepository.save(newAccommodation);
        entityManager.flush();
        assertNotNull(savedAccommodation.getId());
        assertEquals(4, accommodationRepository.count());
    }

    @Test
    void testFindByName() {
        List<Accommodation> accommodations = accommodationRepository.findByName("Luxury Hotel");
        assertEquals(1, accommodations.size());
        assertEquals(a1, accommodations.get(0));
    }

    @Test
    void testFindByNameNotFound() {
        List<Accommodation> accommodations = accommodationRepository.findByName("Non-existent Hotel");
        assertTrue(accommodations.isEmpty());
    }

    @Test
    void testFindByPricePerNightLessThanEqual() {
        List<Accommodation> accommodations = accommodationRepository.findByPricePerNightLessThanEqual(100.0);
        assertEquals(2, accommodations.size());
        assertTrue(accommodations.contains(a2));
        assertTrue(accommodations.contains(a3));
    }

    @Test
    void testFindByAddressContaining() {
        List<Accommodation> accommodations = accommodationRepository.findByAddressContaining("City");
        assertEquals(1, accommodations.size());
        assertEquals(a1, accommodations.get(0));
    }

    @Test
    void testFindByNameContainingIgnoreCase() {
        List<Accommodation> accommodations = accommodationRepository.findByNameContainingIgnoreCase("hotel");
        assertEquals(1, accommodations.size());
        assertEquals(a1, accommodations.get(0));
    }

    @Test
    void testUpdateAccommodation() {
        a1.setName("Updated Luxury Hotel");
        accommodationRepository.save(a1);
        entityManager.flush();
        entityManager.clear();

        Accommodation updatedAccommodation = accommodationRepository.findById(a1.getId()).orElse(null);
        assertNotNull(updatedAccommodation);
        assertEquals("Updated Luxury Hotel", updatedAccommodation.getName());
    }

    @Test
    void testDeleteAccommodation() {
        accommodationRepository.delete(a2);
        entityManager.flush();
        entityManager.clear();

        Accommodation deletedAccommodation = accommodationRepository.findById(a2.getId()).orElse(null);
        assertNull(deletedAccommodation);
        assertEquals(2, accommodationRepository.count());
    }
}