package pja.mas.youssef.travelagency.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import pja.mas.youssef.travelagency.model.customer.Customer;
import pja.mas.youssef.travelagency.model.employee.Agent;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Booking {
    private static double BOOKING_FEE = 100;
    private static double EXTRA_SERVICE_FEE = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Agent agent;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Tour tour;

    @OneToMany(mappedBy = "booking", cascade = {CascadeType.REMOVE})
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<BookingAccommodation> bookingAccommodations = new HashSet<>();

    @OneToMany(mappedBy = "booking", cascade = {CascadeType.REMOVE})
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<TravelTicket> travelTickets = new HashSet<>();

    @NotNull(message = "Booking date is required")
    private LocalDateTime bookingDate;

    @NotNull(message = "Booking start date is required")
    private Status status;

    @NotNull(message = "number of people is required")
    private Integer numberOfPeople;

    @NotBlank(message = "Emergency contact is required")
    private String emergencyContact;

    private Boolean isTravelInsuranceIncluded;

    private Boolean isPrivateBusIncluded;

    private Boolean isMealsIncluded;

    public enum Status {
        DRAFT, REQUEST, PROCESSING, PROCESSED, CONFIRMED, CANCELLED, BOOKED
    }

    public static abstract class BookingBuilder {
        public BookingBuilder isPrivateBusIncluded(Boolean isPrivateBusIncluded) {
            if (this.customer == null) {
                throw new IllegalStateException("Customer must be set before setting isPrivateBusIncluded");
            }
            _validatePrivateBusInclusion(this.customer, isPrivateBusIncluded);
            this.isPrivateBusIncluded = isPrivateBusIncluded;
            return this;
        }
    }

    public void setIsPrivateBusIncluded(Boolean isPrivateBusIncluded) {
        _validatePrivateBusInclusion(this.customer, isPrivateBusIncluded);
        this.isPrivateBusIncluded = isPrivateBusIncluded;
    }

    private static void _validatePrivateBusInclusion(Customer customer, Boolean isPrivateBusIncluded) {
        if (isPrivateBusIncluded != null && isPrivateBusIncluded && !customer.getIsVIP()) {
            throw new IllegalArgumentException("Only VIP customers can have private bus included");
        }
    }

    public Double getTotalPrice() {
        double totalPrice = BOOKING_FEE;
        if (isTravelInsuranceIncluded)
            totalPrice += EXTRA_SERVICE_FEE;
        if (isPrivateBusIncluded)
            totalPrice += EXTRA_SERVICE_FEE;
        if (isMealsIncluded)
            totalPrice += EXTRA_SERVICE_FEE;

        totalPrice += tour.getPricePerSeat() * numberOfPeople;

        for (BookingAccommodation bookingAccommodation : bookingAccommodations) {
            totalPrice += bookingAccommodation.getTotalPrice();
        }

        for (TravelTicket travelTicket : travelTickets) {
            totalPrice += travelTicket.getPrice();
        }

        return totalPrice;
    }
}