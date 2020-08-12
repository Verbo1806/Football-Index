package bg.verbo.footind.web.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultValidator<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultValidator.class);

	public boolean isValid(T entity) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		Set<ConstraintViolation<T>> violations = validator.validate(entity);
		
		for (ConstraintViolation<T> violation : violations) {
			LOGGER.warn(violation.getMessage());
		}
		
		return violations.isEmpty();
	}
}
