package pja.mas.youssef.travelagency.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TravelTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "booking_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Booking booking;

    @NotBlank(message = "Ticket number is required")
    @Column(unique = true)
    private String ticketNumber;

    @NotNull(message = "Price cannot be null")
    private Double price;
}
