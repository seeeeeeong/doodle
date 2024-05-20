package com.seeeeeeong.doodle.common.validator;

import jakarta.validation.Validation;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.Collection;

@Component
public class CustomCollectionValidator implements Validator {

    private final SpringValidatorAdapter validator;

    public CustomCollectionValidator() {
        this.validator = new SpringValidatorAdapter(
                Validation.buildDefaultValidatorFactory().getValidator()
        );
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        for (Object object : (Collection) target) {
            validator.validate(object, errors);
        }
    }
}
