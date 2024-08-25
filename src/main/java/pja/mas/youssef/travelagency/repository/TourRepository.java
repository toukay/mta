package pja.mas.youssef.travelagency.repository;

import org.springframework.data.repository.CrudRepository;
import pja.mas.youssef.travelagency.model.Tour;

public interface TourRepository extends CrudRepository<Tour, Long> {
}
