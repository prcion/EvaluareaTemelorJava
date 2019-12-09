package Validation;

import Validation.ValidationException;

public interface Validator<E> {
    void validate(E entity) throws ValidationException;
}