package pja.mas.youssef.travelagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pja.mas.youssef.travelagency.model.Tour;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {
    List<Tour> findByDestination(String destination);

    @Query("SELECT t FROM Tour t LEFT JOIN FETCH t.events")
    List<Tour> findAllWithEvents();

    @Query("SELECT t FROM Tour t LEFT JOIN FETCH t.events WHERE t.id = :id")
    Tour findByIdWithEvents(@Param("id") Long id);
}
