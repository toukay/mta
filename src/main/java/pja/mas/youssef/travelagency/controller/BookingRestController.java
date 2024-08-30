package pja.mas.youssef.travelagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pja.mas.youssef.travelagency.dto.BookingDTO;
import pja.mas.youssef.travelagency.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingRestController {
    @Autowired
    private BookingService bookingService;

    @GetMapping
    public List<BookingDTO> getAllBookings() {
        return bookingService.getAllBookings();
    }
}