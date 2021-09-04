package br.com.jonas.customer.manager.customer;

import br.com.jonas.customer.manager.customer.domain.Address;
import br.com.jonas.customer.manager.customer.domain.Customer;
import br.com.jonas.customer.manager.customer.domain.dto.Filter;
import br.com.jonas.customer.manager.customer.domain.repository.CustomerRepository;
import br.com.jonas.customer.manager.customer.service.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository repository;


    @BeforeEach
    void setUp() {
        repository.save(Customer.builder().name("test").surname("surname").cpf("11233322211").build());
        repository.save(Customer.builder().name("other").surname("another").cpf("33355548579").build());
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void shouldSaveCustomer() {
        Customer customer = Customer.builder()
                .name("name")
                .surname("surname")
                .cpf("000.000.000-00")
                .addresses(Set.of(Address.builder()
                                .address("Rua teste, 1111")
                                .build()))
                .build();

        Customer persisted = assertDoesNotThrow(() -> customerService.saveCustomer(customer));

        assertNotNull(persisted.getId());
        assertEquals("name", persisted.getName());
        assertEquals("surname", persisted.getSurname());
        assertEquals("00000000000", persisted.getCpf());
    }


    @Test
    void shouldSearchByCpf() {
        Page<Customer> customers = assertDoesNotThrow(() -> customerService.findAll("112", Pageable.unpaged()));

        Customer customer = customers.getContent().get(0);

        assertTrue(customer.getCpf().contains("112"));
    }

    @Test
    void shouldSearchBySurname() {
        Page<Customer> customers = assertDoesNotThrow(() -> customerService.findAll("another", Pageable.unpaged()));

        Customer customer = customers.getContent().get(0);
        assertTrue(customer.getSurname().contains("another"));
    }

    @Test
    void shouldFindByCustomFilters() {
        Filter filter = Filter.builder().search("").startDate(LocalDateTime.now()).build();
        Page<Customer> customers = assertDoesNotThrow(() -> customerService.findByCustomFilter(filter, Pageable.unpaged()));
    }

}
