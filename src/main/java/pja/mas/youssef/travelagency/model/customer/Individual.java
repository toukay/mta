package pja.mas.youssef.travelagency.model.customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Individual extends Customer {

    @NotBlank(message = "PESEL is required")
    @Size(min = 11, max = 11, message = "PESEL must be 11 characters")
    @Column(unique = true)
    private String PESEL;

    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
    private String lastName;

    @Override
    public String getSocietyIdentifier() {
        return PESEL;
    }

    @Override
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
