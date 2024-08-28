package pja.mas.youssef.travelagency.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import pja.mas.youssef.travelagency.model.employee.Driver;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Driver driver;

    @OneToMany(mappedBy = "bus", cascade = {CascadeType.REMOVE})
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<TourBus> tours = new HashSet<>();

    @NotBlank(message = "Model is required")
    @Size(min = 3, max = 50, message = "Model must be between 3 and 50 characters")
    private String model;

    @NotNull(message = "Capacity is required")
    private Integer capacity;

    @NotNull(message = "Location is required")
    @Embedded
    private Location location;
}
