package pja.mas.youssef.travelagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pja.mas.youssef.travelagency.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
