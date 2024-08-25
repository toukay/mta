package pja.mas.youssef.travelagency.repository;

import org.springframework.data.repository.CrudRepository;
import pja.mas.youssef.travelagency.model.Accommodation;

import java.util.List;

public interface AccommodationRepository extends CrudRepository<Accommodation, Long> {
    List<Accommodation> findByName(String name);
    List<Accommodation> findByPricePerNightLessThanEqual(Double price);
    List<Accommodation> findByAddressContaining(String addressPart);
    List<Accommodation> findByNameContainingIgnoreCase(String namePart);
}
