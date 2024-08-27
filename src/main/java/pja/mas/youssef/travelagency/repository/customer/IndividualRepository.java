package pja.mas.youssef.travelagency.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import pja.mas.youssef.travelagency.model.customer.Individual;

public interface IndividualRepository extends JpaRepository<Individual, Long> {
}
