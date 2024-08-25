package pja.mas.youssef.travelagency.model.customer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pja.mas.youssef.travelagency.model.Booking;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Booking> bookings = new HashSet<>();

    @NotBlank(message = "isVIP is required")
    private Boolean isVIP;

    @NotBlank(message = "Email is required")
    @Size(min = 3, max = 255, message = "Email must be between 3 and 255 characters")
    private String email;

    public abstract String getSocietyIdentifier();
    public abstract String getFullName();
}
