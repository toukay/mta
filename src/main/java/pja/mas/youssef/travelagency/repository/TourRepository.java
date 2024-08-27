package pja.mas.youssef.travelagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pja.mas.youssef.travelagency.model.Tour;

public interface TourRepository extends JpaRepository<Tour, Long> {
    public Iterable<Tour> findByDestination(String destination);
}
