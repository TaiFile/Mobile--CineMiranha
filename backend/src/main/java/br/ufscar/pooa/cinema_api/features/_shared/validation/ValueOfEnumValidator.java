package br.ufscar.pooa.cinema_api.features._shared.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Stream;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, String> {
    private List<String> acceptedValues;
    private String errorMessage;

    @Override
    public void initialize(ValueOfEnum annotation) {
        this.acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();

        this.errorMessage = "Invalid value. Accepted values are: [" +
                String.join(", ", acceptedValues) + "]";
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid = acceptedValues.contains(value);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.errorMessage)
                    .addConstraintViolation();
        }

        return isValid;
    }
}
