package Validation;

import Entities.Grade;

public class ValidationNota implements Validator<Grade> {
    public void validate(Grade n) throws ValidationException{
        if(n == null)
            throw new ValidationException("Obiectul nu exista!!!");
        if(n.getGrade() < 1 || n.getGrade() > 10)
            throw  new ValidationException("Nota trebuie sa fie cuprinsa intre 1 si 10!\n");
    }
}
