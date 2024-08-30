package pja.mas.youssef.travelagency.model;

import jakarta.persistence.*;
import lombok.*;
import pja.mas.youssef.travelagency.model.employee.Guide;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tour_id", "role"}),
        @UniqueConstraint(columnNames = {"tour_id", "guide_id"})
})
public class TourGuide {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "guide_id", nullable = false)
    private Guide guide;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Guide.Role role;

    public TourGuide(Tour tour, Guide guide, Guide.Role role) {
        this.tour = tour;
        this.guide = guide;
        this.role = role;
    }
}
