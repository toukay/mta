package pja.mas.youssef.travelagency.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import pja.mas.youssef.travelagency.model.customer.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
