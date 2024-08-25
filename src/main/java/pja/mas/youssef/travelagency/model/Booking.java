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
    private static double bookingFee = 100;
    private static double extraServiceFee = 50;

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

    public Double getTotalPrice() {
        double totalPrice = bookingFee;
        if (isTravelInsuranceIncluded)
            totalPrice += extraServiceFee;
        if (isPrivateBusIncluded)
            totalPrice += extraServiceFee;
        if (isMealsIncluded)
            totalPrice += extraServiceFee;

        return totalPrice;
    }
}
