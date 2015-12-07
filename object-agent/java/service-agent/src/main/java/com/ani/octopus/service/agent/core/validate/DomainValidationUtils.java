package com.ani.octopus.service.agent.core.validate;

import com.ani.octopus.service.agent.service.websocket.account.AccountObject;
import com.ani.octopus.service.agent.service.websocket.dto.AniStub;
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
public class DomainValidationUtils<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainValidationUtils.class);

    private DomainValidationUtils() {}

    public static Validator getInstance() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    public static Set<ValidateMessage> validateAniStub(AniStub aniStub) {
        Set<ValidateMessage> validateMessageSet = new HashSet<>();
        if (aniStub != null) {
            Set<ConstraintViolation<AniStub>> constraintViolations = getInstance().validate(aniStub);
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

    public static boolean isAniStubValid(AniStub aniStub) {
        boolean result = true;
        if (aniStub != null) {
            Set<ConstraintViolation<AniStub>> constraintViolations = getInstance().validate(aniStub);
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

    public static boolean isAccountObjectValid(AccountObject accountObject) {
        boolean result = true;
        if (accountObject != null) {
            Set<ConstraintViolation<AccountObject>> constraintViolations = getInstance().validate(accountObject);
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
}
