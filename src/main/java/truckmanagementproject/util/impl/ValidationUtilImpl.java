package truckmanagementproject.util.impl;

import org.springframework.beans.factory.annotation.Autowired;
import truckmanagementproject.util.ValidationUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public class ValidationUtilImpl implements ValidationUtil {

    private final Validator validator;

    @Autowired
    public ValidationUtilImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <T> boolean isValid(T entity) {

        return this.validator.validate(entity).size() == 0;
    }

    @Override
    public <T> String violations(T entity) {
        Set<ConstraintViolation<T>> violations = this.validator.validate(entity);
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<T> violation : violations) {
            sb.append(violation.getMessage()).append(System.lineSeparator());
        }

        return sb.toString();
    }
}
