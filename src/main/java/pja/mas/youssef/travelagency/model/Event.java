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
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tour_id", nullable = false, updatable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Tour tour;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;
    
    @ElementCollection
    @CollectionTable(name = "event_categories", joinColumns = @JoinColumn(name = "event_id"))
    @Builder.Default
    private Set<Category> categories = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "event_attractions", joinColumns = @JoinColumn(name = "event_id"))
    @Builder.Default
    private Set<String> attractions = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "event_guests", joinColumns = @JoinColumn(name = "event_id"))
    @Builder.Default
    private Set<String> guests = new HashSet<>();

    public enum Category {
        SOCIAL, CULTURE, OTHER
    }

    public Set<String> getActivities() {
        Set<String> activities = new HashSet<>();
        activities.addAll(attractions);
        activities.addAll(guests);
        return activities;
    }
}
