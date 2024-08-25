package pja.mas.youssef.travelagency.model.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pja.mas.youssef.travelagency.model.Tour;
import pja.mas.youssef.travelagency.model.TourGuide;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Guide extends Employee {
//    @ManyToOne
//    @JoinColumn(name = "tour_id")
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private Tour tour;

    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<TourGuide> tourGuides = new HashSet<>();

    @ElementCollection
    @NotEmpty(message = "Guide must know at least one language")
    @CollectionTable(name = "guide_languages", joinColumns = @JoinColumn(name = "guide_id"))
    @Builder.Default
    private Set<String> languages = new HashSet<>();

    @NotNull(message = "Role is required")
    private Role role;

    public enum Role {
        LEADER, HISTORIAN, TRANSLATOR, ENTERTAINER
    }

    public void UpdateCurrentTourActivity() {
        // TODO implement here
    }
}
