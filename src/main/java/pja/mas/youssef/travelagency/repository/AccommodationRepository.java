package pja.mas.youssef.travelagency.repository;

import org.springframework.data.repository.CrudRepository;
import pja.mas.youssef.travelagency.model.Accommodation;

import java.util.List;

public interface AccommodationRepository extends CrudRepository<Accommodation, Long> {
    public List<Accommodation> findByName(String name);
}
