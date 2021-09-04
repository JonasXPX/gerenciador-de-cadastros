package br.com.jonas.customer.manager.customer.service;

import br.com.jonas.customer.manager.config.service.DefaultService;
import br.com.jonas.customer.manager.customer.domain.Customer;
import br.com.jonas.customer.manager.customer.domain.dto.Filter;
import br.com.jonas.customer.manager.customer.domain.repository.CustomerRepository;
import br.com.jonas.customer.manager.customer.service.validator.CustomerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

import static br.com.jonas.customer.manager.customer.service.validator.CustomerValidator.removeNonNumbersFromCpf;

@Service
@RequiredArgsConstructor
public class CustomerService extends DefaultService<Customer, CustomerRepository, CustomerValidator> {

    @Override
    public void defineEntityReferences(Customer entity) {
        Optional.ofNullable(entity.getAddresses())
                .orElse(Collections.emptySet())
                .forEach(address -> address.setCustomer(entity));
    }

    public Page<Customer> findAll(String search, Pageable pageable) {
        return getRepository().findAll(search.replace(' ', '%'), pageable);
    }

    public Page<Customer> findByCustomFilter(Filter filter, Pageable pageable) {
        return getRepository().findAllByCustomFilter(filter, pageable);
    }

    public Optional<Customer> findByCpf(String cpf) {
        return getRepository().findByCpf(removeNonNumbersFromCpf(cpf));
    }

    public Customer saveCustomer(Customer customer) {
        return save(customer);
    }

}
