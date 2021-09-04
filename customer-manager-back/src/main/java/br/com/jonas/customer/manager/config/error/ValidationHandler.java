package br.com.jonas.customer.manager.config.error;

public class ValidationHandler {

    private ValidationHandler() {
    }

    public static ErrorMessage handle(EntityValidationException ex) {
        return ErrorMessage.builder()
                .errors(ex.getErrorMessage().getErrors())
                .build();
    }

}
