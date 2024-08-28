package pja.mas.youssef.travelagency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pja.mas.youssef.travelagency.dto.EventDTO;
import pja.mas.youssef.travelagency.dto.GuideEmployeeDTO;
import pja.mas.youssef.travelagency.dto.TourDTO;
import pja.mas.youssef.travelagency.model.Event;
import pja.mas.youssef.travelagency.model.Tour;
import pja.mas.youssef.travelagency.model.TourGuide;
import pja.mas.youssef.travelagency.model.employee.Guide;
import pja.mas.youssef.travelagency.repository.EventRepository;
import pja.mas.youssef.travelagency.repository.TourRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TourService {
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private EventRepository eventRepository;

    @Transactional(readOnly = true)
    public List<TourDTO> getAllTours() {
        return tourRepository.findAllWithEvents().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Event> getEventsByTourId(Long tourId) {
        return eventRepository.findByTourId(tourId);
    }

    private TourDTO convertToDTO(Tour tour) {
        return TourDTO.builder()
                .id(tour.getId())
                .destination(tour.getDestination())
                .pricePerSeat(tour.getPricePerSeat())
                .startDate(tour.getStartDate())
                .endDate(tour.getEndDate())
                .currentTourActivity(tour.getCurrentTourActivity())
                .events(convertEventsToDTOs(tour.getEvents()))
                .guides(convertTourGuidesToDTOs(tour.getTourGuides()))
                .build();
    }

    private Set<EventDTO> convertEventsToDTOs(Set<Event> events) {
        return events.stream()
                .map(this::convertEventToDTO)
                .collect(Collectors.toSet());
    }

    private EventDTO convertEventToDTO(Event event) {
        return EventDTO.builder()
                .id(event.getId())
                .name(event.getName())
                .categories(event.getCategories())
                .attractions(event.getAttractions())
                .guests(event.getGuests())
                .build();
    }

    private Map<Guide.Role, GuideEmployeeDTO> convertTourGuidesToDTOs(Map<Guide.Role, TourGuide> guides) {
        return guides.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> convertTourGuideToDTO(entry.getValue())));
    }

    private GuideEmployeeDTO convertTourGuideToDTO(TourGuide tourGuide) {
        Guide guide = tourGuide.getGuide();
        return GuideEmployeeDTO.builder()
                .id(guide.getId())
                .firstName(guide.getFirstName())
                .lastName(guide.getLastName())
                .branchAddress(guide.getBranchAddress())
                .languages(guide.getLanguages())
                .role(guide.getRole())
                .build();
    }
}
