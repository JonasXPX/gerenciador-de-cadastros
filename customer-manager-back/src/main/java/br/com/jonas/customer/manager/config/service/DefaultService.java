package br.com.jonas.customer.manager.config.service;

import br.com.jonas.customer.manager.config.error.EntityValidationException;
import br.com.jonas.customer.manager.config.error.ErrorMessage;
import br.com.jonas.customer.manager.config.model.Model;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class DefaultService<E extends Model, R extends JpaRepository<E, Long>, V extends Validator> {

    @Autowired
    @Getter
    protected R repository;

    @Autowired
    protected V validator;

    public E save(E entity) {
        if (nonNull(entity.getId())) {
            return update(entity);
        }
        defineEntityReferences(entity);
        beforeSave(entity);
        executeEntityValidation(entity);
        repository.save(entity);
        afterSave(entity);
        return entity;
    }

    public E update(E entity) {
        if (isNull(entity.getId())) {
            return save(entity);
        }
        defineEntityReferences(entity);
        beforeUpdate(entity);
        executeEntityValidation(entity);
        repository.save(entity);
        afterUpdate(entity);
        return entity;
    }


    public void executeEntityValidation(E entity) {
        ErrorMessage errorMessage = new ErrorMessage();
        validator.validate(entity, errorMessage);

        if (errorMessage.hasErrors()) {
            throw new EntityValidationException(errorMessage);
        }
    }


    public void defineEntityReferences(E entity) {
    }

    public E findById(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private void beforeSave(E entity) {
    }

    private void afterSave(E entity) {
    }

    private void beforeUpdate(E entity) {
    }

    private void afterUpdate(E entity) {
    }
}
