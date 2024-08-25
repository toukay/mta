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
public class Company extends Customer {
    @NotBlank(message = "NIP is required")
    @Size(min = 10, max = 10, message = "NIP must be 10 characters")
    @Column(unique = true)
    private String NIP;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @Override
    public String getSocietyIdentifier() {
        return NIP;
    }

    @Override
    public String getFullName() {
        return name;
    }
}
