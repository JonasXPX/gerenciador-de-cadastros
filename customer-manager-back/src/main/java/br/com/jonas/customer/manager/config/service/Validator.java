package br.com.jonas.customer.manager.config.service;

import br.com.jonas.customer.manager.config.error.ErrorMessage;
import br.com.jonas.customer.manager.config.model.Model;

public interface Validator<E extends Model> {

    void validate(E entity, ErrorMessage errors);

}
