package pja.mas.youssef.travelagency.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pja.mas.youssef.travelagency.dto.TourDTO;
import pja.mas.youssef.travelagency.service.TourService;

import java.util.List;

@Controller
@RequestMapping("/tours")
public class TourController {
    @Autowired
    private TourService tourService;

    @GetMapping
    public String getAllTours(Model model) {
        List<TourDTO> tours = tourService.getAllTours();
        model.addAttribute("tours", tours);
        return "pages/tours";
    }
    @GetMapping("/{id}")
    public String getTourById(@PathVariable Long id, Model model) {
        TourDTO tour = tourService.getTourById(id);
        model.addAttribute("tour", tour);
        return "pages/tour-details";
    }

    @PostMapping("/{id}/select")
    public String selectTour(@PathVariable Long id, @RequestParam String destination, HttpSession session) {
        BookingRequestSessionData bookingData = (BookingRequestSessionData) session.getAttribute("bookingData");
        if (bookingData == null) {
            bookingData = new BookingRequestSessionData();
        }
        bookingData.setCustomerId(BookingRequestSessionData.DEMO_CUSTOMER_ID);
        bookingData.setTourId(id);
        bookingData.setTourDestination(destination);
        session.setAttribute("bookingData", bookingData);
        return "redirect:/bookings/create-new-request";
    }
}
