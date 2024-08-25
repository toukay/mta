package pja.mas.youssef.travelagency.repository.customer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pja.mas.youssef.travelagency.model.customer.Company;
import pja.mas.youssef.travelagency.model.customer.Customer;
import pja.mas.youssef.travelagency.model.customer.Individual;

import java.util.Optional;

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

    Individual individual;
    Company company;

    @BeforeEach
    void setUp() {
        individual = Individual.builder()
                .firstName("John")
                .lastName("Doe")
                .PESEL("12345678901")
                .email("john.doe@example.com")
                .isVIP(false)
                .build();

        company = Company.builder()
                .name("Company 1")
                .NIP("1234567890")
                .email("company1@example.com")
                .isVIP(true)
                .build();
    }

    @Test
    void testCustomerDependencies() {
        assertNotNull(customerRepository);
        assertNotNull(individualRepository);
        assertNotNull(companyRepository);
    }

    @Test
    void testSaveAllCustomers() {
        individualRepository.save(individual);
        companyRepository.save(company);
        entityManager.flush();
        long count = customerRepository.count();
        assertEquals(2, count);
    }

    @Test
    void testFindIndividualCustomer() {
        Individual savedIndividual = individualRepository.save(individual);
        entityManager.flush();
        Optional<Customer> foundCustomer = customerRepository.findById(savedIndividual.getId());
        assertTrue(foundCustomer.isPresent());
        assertTrue(foundCustomer.get() instanceof Individual);
        assertEquals(individual.getPESEL(), ((Individual) foundCustomer.get()).getPESEL());
    }

    @Test
    void testFindCompanyCustomer() {
        Company savedCompany = companyRepository.save(company);
        entityManager.flush();
        Optional<Customer> foundCustomer = customerRepository.findById(savedCompany.getId());
        assertTrue(foundCustomer.isPresent());
        assertTrue(foundCustomer.get() instanceof Company);
        assertEquals(company.getNIP(), ((Company) foundCustomer.get()).getNIP());
    }

    @Test
    void testUpdateIndividualCustomer() {
        Individual savedIndividual = individualRepository.save(individual);
        savedIndividual.setFirstName("Jane");
        savedIndividual.setIsVIP(true);
        individualRepository.save(savedIndividual);
        entityManager.flush();
        entityManager.clear();

        Optional<Customer> updatedCustomer = customerRepository.findById(savedIndividual.getId());
        assertTrue(updatedCustomer.isPresent());
        assertTrue(updatedCustomer.get() instanceof Individual);
        assertEquals("Jane", ((Individual) updatedCustomer.get()).getFirstName());
        assertTrue(updatedCustomer.get().getIsVIP());
    }

    @Test
    void testDeleteCustomer() {
        Company savedCompany = companyRepository.save(company);
        long initialCount = customerRepository.count();
        customerRepository.delete(savedCompany);
        entityManager.flush();
        long finalCount = customerRepository.count();
        assertEquals(initialCount - 1, finalCount);
    }

    @Test
    void testGetSocietyIdentifier() {
        Individual savedIndividual = individualRepository.save(individual);
        Company savedCompany = companyRepository.save(company);
        entityManager.flush();
        entityManager.clear();

        Customer fetchedIndividual = customerRepository.findById(savedIndividual.getId()).orElseThrow();
        Customer fetchedCompany = customerRepository.findById(savedCompany.getId()).orElseThrow();

        assertEquals(individual.getPESEL(), fetchedIndividual.getSocietyIdentifier());
        assertEquals(company.getNIP(), fetchedCompany.getSocietyIdentifier());
    }

    @Test
    void testGetFullName() {
        Individual savedIndividual = individualRepository.save(individual);
        Company savedCompany = companyRepository.save(company);
        entityManager.flush();
        entityManager.clear();

        Customer fetchedIndividual = customerRepository.findById(savedIndividual.getId()).orElseThrow();
        Customer fetchedCompany = customerRepository.findById(savedCompany.getId()).orElseThrow();

        assertEquals(individual.getFirstName() + " " + individual.getLastName(), fetchedIndividual.getFullName());
        assertEquals(company.getName(), fetchedCompany.getFullName());
    }

    @Test
    void testUniqueConstraints() {
        individualRepository.save(individual);
        companyRepository.save(company);
        entityManager.flush();

        Individual duplicateIndividual = Individual.builder()
                .firstName("Jane")
                .lastName("Doe")
                .PESEL(individual.getPESEL())  // Same PESEL as existing individual
                .email("jane.doe@example.com")
                .isVIP(false)
                .build();

        Company duplicateCompany = Company.builder()
                .name("Company 2")
                .NIP(company.getNIP())  // Same NIP as existing company
                .email("company2@example.com")
                .isVIP(false)
                .build();

        assertThrows(Exception.class, () -> individualRepository.save(duplicateIndividual));
        assertThrows(Exception.class, () -> companyRepository.save(duplicateCompany));
    }
}