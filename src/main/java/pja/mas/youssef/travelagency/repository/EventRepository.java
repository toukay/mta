package pja.mas.youssef.travelagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pja.mas.youssef.travelagency.model.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    public List<Event> findByName(String name);
    List<Event> findByTourId(Long tourId);
}
