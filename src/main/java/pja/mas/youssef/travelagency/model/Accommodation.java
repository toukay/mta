package pja.mas.youssef.travelagency.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "accommodation", cascade = {CascadeType.REMOVE})
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<BookingAccommodation> bookingAccommodations = new HashSet<>();

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "Address is required")
    @Size(min = 3, max = 255, message = "Address must be between 3 and 255 characters")
    private String address;

    @NotBlank(message = "Price per night is required")
    private Double pricePerNight;
}
