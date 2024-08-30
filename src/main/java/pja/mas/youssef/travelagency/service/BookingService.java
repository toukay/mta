package pja.mas.youssef.travelagency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pja.mas.youssef.travelagency.session.BookingRequestSessionData;
import pja.mas.youssef.travelagency.dto.BookingAccommodationDTO;
import pja.mas.youssef.travelagency.dto.BookingDTO;
import pja.mas.youssef.travelagency.dto.TravelTicketDTO;
import pja.mas.youssef.travelagency.model.Booking;
import pja.mas.youssef.travelagency.model.BookingAccommodation;
import pja.mas.youssef.travelagency.model.TravelTicket;
import pja.mas.youssef.travelagency.repository.BookingRepository;
import pja.mas.youssef.travelagency.repository.TourRepository;
import pja.mas.youssef.travelagency.repository.customer.CustomerRepository;
import pja.mas.youssef.travelagency.repository.employee.AgentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private AgentRepository agentRepository;

    @Transactional(readOnly = true)
    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookingDTO> getDraftBookingsForCustomer(Long customerId) {
        return bookingRepository.findByCustomerIdAndStatus(customerId, Booking.Status.DRAFT)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Booking createDraftBooking(BookingRequestSessionData bookingData) {
        Booking booking = Booking.builder()
                .customer(customerRepository.findById(bookingData.getCustomerId()).orElseThrow())
                .tour(tourRepository.findById(bookingData.getTourId()).orElseThrow())
                .agent(agentRepository.findById(1L).orElseThrow())
                .status(Booking.Status.DRAFT)
                .bookingDate(LocalDateTime.now())
                .numberOfPeople(bookingData.getNumberOfPeople())
                .emergencyContact(bookingData.getEmergencyContactName() + " - " + bookingData.getEmergencyContactNumber())
                .isTravelInsuranceIncluded(bookingData.getIsTravelInsuranceIncluded())
                .isPrivateBusIncluded(bookingData.getIsPrivateBusIncluded())
                .isMealsIncluded(bookingData.getIsMealsIncluded())
                .build();

        bookingRepository.save(booking);

        return booking;
    }

    @Transactional
    public void forwardBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() != Booking.Status.DRAFT) {
            throw new IllegalStateException("Only DRAFT bookings can be forwarded");
        }

        booking.setStatus(Booking.Status.REQUEST);
        bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public BookingDTO getDraftBookingById(Long id) {
        return bookingRepository.findByIdAndStatus(id, Booking.Status.DRAFT)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public BookingRequestSessionData convertDTOToSessionData(BookingDTO bookingDTO) {
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

    private BookingDTO convertToDTO(Booking booking) {
        return BookingDTO.builder()
                .id(booking.getId())
                .customerId(booking.getCustomer().getId())
                .agentId(booking.getAgent().getId())
                .tourId(booking.getTour().getId())
                .tourDestination(booking.getTour().getDestination())
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
