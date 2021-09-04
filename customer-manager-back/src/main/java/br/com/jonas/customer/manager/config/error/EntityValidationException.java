package br.com.jonas.customer.manager.config.error;

import lombok.Getter;

public class EntityValidationException extends RuntimeException {

    @Getter
    private final ErrorMessage errorMessage;

    public EntityValidationException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}
