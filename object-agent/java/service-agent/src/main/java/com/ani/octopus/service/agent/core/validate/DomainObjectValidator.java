package com.ani.octopus.service.agent.core.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by zhaoyu on 15-12-7.
 */
public class DomainObjectValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainObjectValidator.class);

    public static Validator getInstance() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    public static <T>  boolean isDomainObjectValid(T object) {
        boolean result = true;
        if (object != null) {
            Set<ConstraintViolation<T>> constraintViolations = getInstance().validate(object);
            if (constraintViolations.size() > 0) {
                result = false;
                for (ConstraintViolation violation : constraintViolations) {
                    LOGGER.info("filed={}, msg={}, currentValue={}.",
                            violation.getPropertyPath().toString(),
                            Objects.toString(violation.getMessage()),
                            Objects.toString(violation.getInvalidValue())
                    );
                }
            }
        }
        return result;
    }

    public static <T> Set<ValidateMessage> validateDomainObject(T object) {
        Set<ValidateMessage> validateMessageSet = new HashSet<>();
        if (object != null) {
            Set<ConstraintViolation<T>> constraintViolations = getInstance().validate(object);
            if (constraintViolations.size() > 0) {
                for (ConstraintViolation violation : constraintViolations) {
                    ValidateMessage message = new ValidateMessage(
                            violation.getPropertyPath().toString(),
                            Objects.toString(violation.getMessage()),
                            Objects.toString(violation.getInvalidValue())
                    );
                    validateMessageSet.add(message);
                }
            }
        }
        return validateMessageSet;
    }
}
