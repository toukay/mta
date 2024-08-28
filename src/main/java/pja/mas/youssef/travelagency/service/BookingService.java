package pja.mas.youssef.travelagency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pja.mas.youssef.travelagency.dto.BookingAccommodationDTO;
import pja.mas.youssef.travelagency.dto.BookingDTO;
import pja.mas.youssef.travelagency.dto.TravelTicketDTO;
import pja.mas.youssef.travelagency.model.Booking;
import pja.mas.youssef.travelagency.model.BookingAccommodation;
import pja.mas.youssef.travelagency.model.TravelTicket;
import pja.mas.youssef.travelagency.repository.BookingRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Transactional(readOnly = true)
    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private BookingDTO convertToDTO(Booking booking) {
        return BookingDTO.builder()
                .id(booking.getId())
                .customerId(booking.getCustomer().getId())
                .agentId(booking.getAgent().getId())
                .bookingDate(booking.getBookingDate())
                .status(booking.getStatus())
                .numberOfPeople(booking.getNumberOfPeople())
                .emergencyContact(booking.getEmergencyContact())
                .isTravelInsuranceIncluded(booking.getIsTravelInsuranceIncluded())
                .isPrivateBusIncluded(booking.getIsPrivateBusIncluded())
                .isMealsIncluded(booking.getIsMealsIncluded())
                .travelTickets(convertTravelTicketsToDTOs(booking.getTravelTickets()))
                .accommodations(convertBookingAccommodationsToDTOs(booking.getBookingAccommodations()))
                .totalPrice(booking.getTotalPrice())
                .build();
    }

    private Set<TravelTicketDTO> convertTravelTicketsToDTOs(Set<TravelTicket> travelTickets) {
        return travelTickets.stream()
                .map(this::convertTravelTicketToDTO)
                .collect(Collectors.toSet());
    }

    private TravelTicketDTO convertTravelTicketToDTO(TravelTicket travelTicket) {
        return TravelTicketDTO.builder()
                .id(travelTicket.getId())
                .ticketNumber(travelTicket.getTicketNumber())
                .price(travelTicket.getPrice())
                .build();
    }

    private Set<BookingAccommodationDTO> convertBookingAccommodationsToDTOs(Set<BookingAccommodation> bookingAccommodations) {
        return bookingAccommodations.stream()
                .map(this::convertBookingAccommodationToDTO)
                .collect(Collectors.toSet());
    }

    private BookingAccommodationDTO convertBookingAccommodationToDTO(BookingAccommodation bookingAccommodation) {
        return BookingAccommodationDTO.builder()
                .id(bookingAccommodation.getId())
                .bookingId(bookingAccommodation.getBooking().getId())
                .accommodationId(bookingAccommodation.getAccommodation().getId())
                .accommodationName(bookingAccommodation.getAccommodation().getName())
                .accommodationAddress(bookingAccommodation.getAccommodation().getAddress())
                .pricePerNight(bookingAccommodation.getAccommodation().getPricePerNight())
                .checkInDate(bookingAccommodation.getCheckInDate())
                .checkOutDate(bookingAccommodation.getCheckOutDate())
                .numberOfNights(bookingAccommodation.getNumberOfNights())
                .totalPrice(bookingAccommodation.getTotalPrice())
                .build();
    }
}
