package br.com.jonas.customer.manager.config.error;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Builder
@ToString
public class ErrorMessage {

    @Setter
    @Getter
    private String message;

    @Getter
    private final Map<String, String> errors;

    public ErrorMessage() {
        errors = new HashMap<>();
    }


    public ErrorMessage(String message, Map<String, String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public void addFieldError(String field, String message) {
        errors.put(field, message);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
