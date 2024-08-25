package pja.mas.youssef.travelagency.repository.customer;

import org.springframework.data.repository.CrudRepository;
import pja.mas.youssef.travelagency.model.customer.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
