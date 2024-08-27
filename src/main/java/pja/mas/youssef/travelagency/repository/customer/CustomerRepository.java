package pja.mas.youssef.travelagency.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import pja.mas.youssef.travelagency.model.customer.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
