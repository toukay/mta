package pja.mas.youssef.travelagency.repository;

import org.springframework.data.repository.CrudRepository;
import pja.mas.youssef.travelagency.model.TravelTicket;

public interface TravelTicketRepository extends CrudRepository<TravelTicket, Long> {
    public TravelTicket findByTicketNumber(String ticketNumber);
}
