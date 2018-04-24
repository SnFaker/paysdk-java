package com.github.superkoh.paysdk.utils;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

public class ConstraintViolationUtils {

  private final static Validator validator = Validation.buildDefaultValidatorFactory()
      .getValidator();

  public static <T> void validate(T object) {
    Set<ConstraintViolation<T>> violations = validator.validate(object);
    if (violations.size() > 0) {
      ConstraintViolation<T> firstViolation = violations.iterator().next();
      throw new ConstraintViolationException(
          firstViolation.getPropertyPath().toString() + " " + firstViolation.getMessage(),
          violations);
    }
  }
}
