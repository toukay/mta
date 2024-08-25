package pja.mas.youssef.travelagency.repository;

import org.springframework.data.repository.CrudRepository;
import pja.mas.youssef.travelagency.model.Event;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {
    public List<Event> findByName(String name);
}
