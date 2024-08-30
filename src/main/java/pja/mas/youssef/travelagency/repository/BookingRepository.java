package pja.mas.youssef.travelagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pja.mas.youssef.travelagency.model.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerIdAndStatus(Long customerId, Booking.Status status);

    Optional<Booking> findByIdAndStatus(Long id, Booking.Status status);
}
