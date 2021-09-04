package br.com.jonas.customer.manager.customer.service.validator;

import br.com.jonas.customer.manager.config.error.ErrorMessage;
import br.com.jonas.customer.manager.config.service.Validator;
import br.com.jonas.customer.manager.customer.domain.Address;
import br.com.jonas.customer.manager.customer.domain.Customer;
import br.com.jonas.customer.manager.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.hasText;

@Component
@RequiredArgsConstructor
public class CustomerValidator implements Validator<Customer> {

    private final CustomerService service;

    @Override
    public void validate(Customer entity, ErrorMessage errors) {
        validateEmptyOrNullFields(entity, errors);

        if (!errors.hasErrors()) {
            entity.setCpf(removeNonNumbersFromCpf(entity.getCpf()));
            verifyCpfDuplicity(entity, errors);
        }
    }

    private void validateEmptyOrNullFields(Customer entity, ErrorMessage errors) {
        if (!hasText(entity.getCpf())) {
            errors.addFieldError("cpf", "CPF é obrigatório");
        }

        if (!hasText(entity.getName())) {
            errors.addFieldError("name", "Nome é obrigatório");
        }

        if (!hasText(entity.getSurname())) {
            errors.addFieldError("surname", "Sobrenome é obrigatório");
        }

        filterAndRemoveEmptyAddress(entity);

        if (isNull(entity.getAddresses()) || entity.getAddresses().isEmpty()) {
            errors.addFieldError("address",  "Endereço é obrigatório");
        }
    }


    private void verifyCpfDuplicity(Customer entity, ErrorMessage errors) {
        Optional<Customer> byCpf = service.findByCpf(entity.getCpf());
        if (!byCpf.isPresent()) {
            return;
        }

        if (entity.equals(byCpf.get())) {
            return;
        }

        errors.addFieldError("cpf", "CPF já cadastrado");
    }

    private void filterAndRemoveEmptyAddress(Customer entity) {
        Set<Address> collect = Optional.ofNullable(entity.getAddresses())
                .orElse(Collections.emptySet())
                .stream()
                .filter(address -> hasText(address.getAddress()))
                .collect(Collectors.toSet());

        entity.setAddresses(collect);
    }

    public static String removeNonNumbersFromCpf(String cpf) {
        return cpf.replaceAll("\\D", "");
    }
}
