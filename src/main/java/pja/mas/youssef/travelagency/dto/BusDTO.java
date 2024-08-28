package pja.mas.youssef.travelagency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import pja.mas.youssef.travelagency.model.Location;

@Data
@Builder
public class BusDTO {
    @JsonProperty("bus_id")
    private Long id;
    private String model;
    private Integer capacity;
    private Double LocationLongitude;
    private Double LocationLatitude;
}
