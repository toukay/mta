package pja.mas.youssef.travelagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pja.mas.youssef.travelagency.dto.BookingDTO;
import pja.mas.youssef.travelagency.dto.TourDTO;
import pja.mas.youssef.travelagency.model.Booking;
import pja.mas.youssef.travelagency.repository.BookingRepository;
import pja.mas.youssef.travelagency.service.BookingService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping
    public List<BookingDTO> getAllBookings() {
        return bookingService.getAllBookings();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
//        Optional<Booking> booking = bookingRepository.findById(id);
//        if (booking.isPresent()) {
//            return ResponseEntity.ok(booking.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping
//    public Booking createBooking(@RequestBody Booking booking) {
//        return bookingRepository.save(booking);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking bookingDetails) {
//        Optional<Booking> booking = bookingRepository.findById(id);
//        if (booking.isPresent()) {
//            Booking updatedBooking = booking.get();
//            updatedBooking.setStatus(bookingDetails.getStatus());
//            updatedBooking.setNumberOfPeople(bookingDetails.getNumberOfPeople());
//            updatedBooking.setEmergencyContact(bookingDetails.getEmergencyContact());
//            updatedBooking.setIsTravelInsuranceIncluded(bookingDetails.getIsTravelInsuranceIncluded());
//            updatedBooking.setIsPrivateBusIncluded(bookingDetails.getIsPrivateBusIncluded());
//            updatedBooking.setIsMealsIncluded(bookingDetails.getIsMealsIncluded());
//            return ResponseEntity.ok(bookingRepository.save(updatedBooking));
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
//        Optional<Booking> booking = bookingRepository.findById(id);
//        if (booking.isPresent()) {
//            bookingRepository.delete(booking.get());
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}