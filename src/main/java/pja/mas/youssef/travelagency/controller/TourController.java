package pja.mas.youssef.travelagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pja.mas.youssef.travelagency.dto.TourDTO;
import pja.mas.youssef.travelagency.service.TourService;

import java.util.List;

@RestController
@RequestMapping("/api/tours")
public class TourController {
    @Autowired
    private TourService tourService;

    @GetMapping
    public List<TourDTO> getAllTours(){
        return tourService.getAllTours();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Tour> getTourById(@PathVariable Long id){
//        Optional<Tour> tour = tourService.findById(id);
//        return tour.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
}
