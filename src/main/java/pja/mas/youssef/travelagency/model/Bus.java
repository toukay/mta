package pja.mas.youssef.travelagency.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @NotBlank(message = "Model is required")
    @Size(min = 3, max = 50, message = "Model must be between 3 and 50 characters")
    private String model;

    @NotNull(message = "Capacity is required")
    private Integer capacity;

    @NotNull(message = "Location is required")
    @Embedded
    private Location location;
}
