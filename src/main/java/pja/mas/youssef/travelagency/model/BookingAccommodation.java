package pja.mas.youssef.travelagency.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import pja.mas.youssef.travelagency.model.customer.Customer;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BookingAccommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "accommodation_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Accommodation accommodation;

    @NotNull(message = "Check-in date is required")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    private LocalDate checkOutDate;

    public int getNumberOfNights() {
        return (int) (checkOutDate.toEpochDay() - checkInDate.toEpochDay());
    }
}
