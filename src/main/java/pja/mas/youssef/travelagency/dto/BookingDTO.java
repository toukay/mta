package pja.mas.youssef.travelagency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import pja.mas.youssef.travelagency.model.Booking;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class BookingDTO {
    @JsonProperty("booking_id")
    private Long id;
    @JsonProperty("customer_id")
    private Long customerId;
    @JsonProperty("Agent_id")
    private Long agentId;
    @JsonProperty("Tour_id")
    private LocalDateTime bookingDate;
    private Booking.Status status;
    private Integer numberOfPeople;
    private String emergencyContact;
    private Boolean isTravelInsuranceIncluded;
    private Boolean isPrivateBusIncluded;
    private Boolean isMealsIncluded;
    private Set<TravelTicketDTO> travelTickets;
    private Set<BookingAccommodationDTO> accommodations;
    private Double totalPrice;
}
