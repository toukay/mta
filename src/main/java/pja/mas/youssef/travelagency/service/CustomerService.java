package pja.mas.youssef.travelagency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pja.mas.youssef.travelagency.dto.CustomerDTO;
import pja.mas.youssef.travelagency.model.customer.Customer;
import pja.mas.youssef.travelagency.repository.customer.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return CustomerDTO.builder()
                .id(customer.getId())
                .socialIdentifier(customer.getSocietyIdentifier())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .isVIP(customer.getIsVIP())
                .build();
    }
}