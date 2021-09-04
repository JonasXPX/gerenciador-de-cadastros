package br.com.jonas.customer.manager.customer.web;

import br.com.jonas.customer.manager.customer.domain.Customer;
import br.com.jonas.customer.manager.customer.domain.dto.Filter;
import br.com.jonas.customer.manager.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static java.lang.String.format;

@RequiredArgsConstructor
@RestController
@RequestMapping("customer")
@CrossOrigin("*")
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    public ResponseEntity<Page<Customer>> findAll(String search, Pageable pageable) {
        return ResponseEntity.ok(service.findAll(search, pageable));
    }

    @PostMapping("advance-filter")
    public ResponseEntity<Page<Customer>> findAll(@RequestBody Filter filter, Pageable pageable) {
        return ResponseEntity.ok(service.findByCustomFilter(filter, pageable));
    }


    @GetMapping("{id}")
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Customer> save(@RequestBody Customer customer) {
        Customer persisted = service.saveCustomer(customer);

        return ResponseEntity
                .created(createUriWithId(persisted.getId()))
                .body(persisted);
    }

    @PutMapping
    public ResponseEntity<Customer> update(@RequestBody Customer customer) {
        return ResponseEntity.ok(service.saveCustomer(customer));
    }

    @DeleteMapping("{id}")
    public void delete (@PathVariable Long id) {
        service.deleteById(id);
    }


    private URI createUriWithId(Long id) {
        return URI.create(format("customer/%s", id));
    }


}
