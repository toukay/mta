package pja.mas.youssef.travelagency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TravelTicketDTO {
    @JsonProperty("ticket_id")
    private Long id;
    private String ticketNumber;
    private Double price;
}
