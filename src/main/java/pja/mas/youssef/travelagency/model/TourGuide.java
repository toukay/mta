package pja.mas.youssef.travelagency.model;

import jakarta.persistence.*;
import lombok.*;
import pja.mas.youssef.travelagency.model.employee.Guide;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourGuide {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "guide_id")
    private Guide guide;

    @Enumerated(EnumType.STRING)
    private Guide.Role role;

    public TourGuide(Tour tour, Guide guide, Guide.Role role) {
        this.tour = tour;
        this.guide = guide;
        this.role = role;
    }
}
