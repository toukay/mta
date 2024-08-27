package pja.mas.youssef.travelagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pja.mas.youssef.travelagency.model.TravelTicket;

public interface TravelTicketRepository extends JpaRepository<TravelTicket, Long> {
    public TravelTicket findByTicketNumber(String ticketNumber);
}
