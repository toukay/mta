package pja.mas.youssef.travelagency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import pja.mas.youssef.travelagency.model.employee.Guide;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Data
@Builder
public class TourDTO {
    @JsonProperty("tour_id")
    private Long id;
    private String destination;
    private Double pricePerSeat;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String currentTourActivity;
    private Set<EventDTO> events;
    private Map<Guide.Role, GuideEmployeeDTO> guides;
}