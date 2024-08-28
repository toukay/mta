package pja.mas.youssef.travelagency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import pja.mas.youssef.travelagency.model.Event;

import java.util.Set;

@Data
@Builder
public class EventDTO {
    @JsonProperty("event_id")
    private Long id;
    private String name;
    private Set<Event.Category> categories;
    private Set<String> attractions;
    private Set<String> guests;
}
