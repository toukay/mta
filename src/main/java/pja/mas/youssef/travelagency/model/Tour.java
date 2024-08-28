package pja.mas.youssef.travelagency.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import pja.mas.youssef.travelagency.model.employee.Guide;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Booking> bookings = new HashSet<>();

    @OneToMany(mappedBy = "tour", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Event> events = new HashSet<>();

    @OneToMany(mappedBy = "tour", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyEnumerated(EnumType.STRING)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Map<Guide.Role, TourGuide> tourGuides = new EnumMap<>(Guide.Role.class);

    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<TourBus> tourBuses = new HashSet<>();

    @NotBlank(message = "Name is required")
    private String destination;

    @NotNull(message = "Price per seat cannot be null")
    private Double pricePerSeat;

    @NotNull(message = "Start Date cannot be null")
    private LocalDateTime startDate;

    @NotNull(message = "End Date cannot be null")
    private LocalDateTime endDate;

    private String currentTourActivity;
}
