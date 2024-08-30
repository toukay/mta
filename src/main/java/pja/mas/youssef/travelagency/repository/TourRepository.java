package pja.mas.youssef.travelagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pja.mas.youssef.travelagency.model.Tour;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {
    List<Tour> findByDestination(String destination);
}
