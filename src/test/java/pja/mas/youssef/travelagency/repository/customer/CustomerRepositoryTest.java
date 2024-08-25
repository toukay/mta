package pja.mas.youssef.travelagency.repository.customer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pja.mas.youssef.travelagency.model.customer.Company;
import pja.mas.youssef.travelagency.model.customer.Individual;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private IndividualRepository individualRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @PersistenceContext
    private EntityManager entityManager;

    Individual i1;
    Company c1;

    @BeforeEach
    void setUp() {
        i1 = Individual.builder()
                .firstName("John")
                .lastName("Doe")
                .PESEL("12345678901")
                .build();

        c1 = Company.builder()
                .name("Company 1")
                .NIP("1234567890")
                .build();
    }

    @Test
    void testCustomerDependencies(){
        assertNotNull(customerRepository);
        assertNotNull(individualRepository);
        assertNotNull(companyRepository);
    }

    @Test
    void testSaveAllCustomers(){
        individualRepository.save(i1);
        companyRepository.save(c1);
        entityManager.flush();
        long count = customerRepository.count();
        assertEquals(2, count);
    }
}