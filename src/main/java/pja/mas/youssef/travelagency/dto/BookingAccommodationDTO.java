package pja.mas.youssef.travelagency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookingAccommodationDTO {
    @JsonProperty("booking_accommodation_id")
    private Long id;
    @JsonProperty("Booking_id")
    private Long bookingId;
    @JsonProperty("accommodation_id")
    private Long accommodationId;
    private String accommodationName;
    private String accommodationAddress;
    private Double pricePerNight;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfNights;
    private double totalPrice;
}
