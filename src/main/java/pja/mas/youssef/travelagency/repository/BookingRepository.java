package pja.mas.youssef.travelagency.repository;

import org.springframework.data.repository.CrudRepository;
import pja.mas.youssef.travelagency.model.Booking;

public interface BookingRepository extends CrudRepository<Booking, Long> {
}
