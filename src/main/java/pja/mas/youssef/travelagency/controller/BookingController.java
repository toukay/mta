package pja.mas.youssef.travelagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pja.mas.youssef.travelagency.dto.BookingDTO;
import pja.mas.youssef.travelagency.dto.CustomerDTO;
import pja.mas.youssef.travelagency.model.Booking;
import pja.mas.youssef.travelagency.service.BookingService;

import jakarta.servlet.http.HttpSession;
import pja.mas.youssef.travelagency.service.CustomerService;

import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/drafts")
    public String getDraftBookings(Model model) {
        List<BookingDTO> draftBookings = bookingService.getDraftBookingsForCustomer(BookingRequestSessionData.DEMO_CUSTOMER_ID);
        model.addAttribute("draftBookings", draftBookings);
        return "pages/draft-bookings";
    }

    @GetMapping("/create-new-request")
    public String showBookingForm(Model model, HttpSession session) {
        BookingRequestSessionData sessionBookingData = (BookingRequestSessionData) session.getAttribute("bookingData");
        if (sessionBookingData == null || sessionBookingData.getTourId() == null) {
            return "redirect:/tours";
        }
        model.addAttribute("bookingData", sessionBookingData);
        return "pages/booking-request-main-form";
    }

    @PostMapping("/create-new-request-extras")
    public String showBookingExtrasForm(Model model, @ModelAttribute BookingRequestSessionData bookingData, HttpSession session) {
        BookingRequestSessionData sessionBookingData = (BookingRequestSessionData) session.getAttribute("bookingData");
        sessionBookingData.setNumberOfPeople(bookingData.getNumberOfPeople());
        sessionBookingData.setEmergencyContactName(bookingData.getEmergencyContactName());
        sessionBookingData.setEmergencyContactNumber(bookingData.getEmergencyContactNumber());
        CustomerDTO customer = customerService.getCustomerById(sessionBookingData.getCustomerId());
        session.setAttribute("bookingData", sessionBookingData);
        model.addAttribute("bookingData", sessionBookingData);
        model.addAttribute("isCustomerVIP", customer.getIsVIP());
        return "pages/booking-request-extras-form";
    }

    @PostMapping("/create-new-request-summary")
    public String showBookingSummary(Model model, @ModelAttribute BookingRequestSessionData bookingData, HttpSession session) {
        BookingRequestSessionData sessionBookingData = (BookingRequestSessionData) session.getAttribute("bookingData");
        sessionBookingData.setIsTravelInsuranceIncluded(bookingData.getIsTravelInsuranceIncluded());
        sessionBookingData.setIsMealsIncluded(bookingData.getIsMealsIncluded());
        sessionBookingData.setIsPrivateBusIncluded(bookingData.getIsPrivateBusIncluded() != null && bookingData.getIsPrivateBusIncluded());
        Booking booking = bookingService.createDraftBooking(sessionBookingData);
        sessionBookingData.setTotalPrice(booking.getTotalPrice());
        session.setAttribute("bookingData", sessionBookingData);
        model.addAttribute("bookingData", sessionBookingData);
        model.addAttribute("bookingModelID", booking.getId());
        return "pages/booking-request-summary";
    }

    @PostMapping("/save-booking-request")
    public String saveBooking(@ModelAttribute BookingRequestSessionData bookingData, HttpSession session) {
        BookingRequestSessionData sessionBookingData = (BookingRequestSessionData) session.getAttribute("bookingData");
        session.removeAttribute("bookingData");
        return "redirect:/bookings/drafts";
    }

    @PostMapping("/forward-booking-request")
    public String forwardBooking(@RequestParam("bookingId") Long bookingId, HttpSession session) {
        try {
            bookingService.forwardBooking(bookingId);
            session.removeAttribute("bookingData");
            return "redirect:/bookings/drafts";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

    @GetMapping("/submit/{id}")
    public String submitDraftBooking(@PathVariable Long id, Model model, HttpSession session) {
        BookingDTO bookingDTO = bookingService.getDraftBookingById(id);
        if (bookingDTO == null) {
            return "redirect:/bookings/drafts";
        }
        BookingRequestSessionData bookingData = convertDTOToSessionData(bookingDTO);
        session.setAttribute("bookingData", bookingData);
        model.addAttribute("bookingData", bookingData);
        model.addAttribute("bookingModelID", bookingDTO.getId());
        return "pages/booking-request-summary";
    }

    private BookingRequestSessionData convertDTOToSessionData(BookingDTO bookingDTO) {
        return BookingRequestSessionData.builder()
                .customerId(bookingDTO.getCustomerId())
                .tourId(bookingDTO.getId())
                .tourDestination(bookingDTO.getTourDestination())
                .numberOfPeople(bookingDTO.getNumberOfPeople())
                .emergencyContactName(bookingDTO.getEmergencyContact().split(" - ")[0])
                .emergencyContactNumber(bookingDTO.getEmergencyContact().split(" - ")[1])
                .isTravelInsuranceIncluded(bookingDTO.getIsTravelInsuranceIncluded())
                .isPrivateBusIncluded(bookingDTO.getIsPrivateBusIncluded())
                .isMealsIncluded(bookingDTO.getIsMealsIncluded())
                .totalPrice(bookingDTO.getTotalPrice())
                .build();
    }
}