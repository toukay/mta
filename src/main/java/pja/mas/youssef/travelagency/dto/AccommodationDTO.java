package pja.mas.youssef.travelagency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AccommodationDTO {
    @JsonProperty("accommodation_id")
    private Long id;
    private String name;
    private String address;
    private Double pricePerNight;
}
