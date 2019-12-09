package Validation;

import Entities.Homework;

public class ValidationTema implements Validator<Homework> {

    @Override
    public void validate(Homework t) throws ValidationException {
        String err="";

        if (t == null)
            throw  new ValidationException("Entitatea nu exista!!!");

        if (t.getStartWeek() < 1 || t.getStartWeek() > 14)
            err += "Saptamana de inceput trebuie sa fie intre 1 si 14!\n";

        if (t.getDeadlineWeek() < 1 || t.getDeadlineWeek() > 14)
            err += "Saptamana de sfarsit trebuie sa fie intre 1 si 14!\n";

        if (t.getStartWeek() >= t.getDeadlineWeek())
            err += "Saptamana de inceput trebuie sa fie mai mica decat cea de sfarsit!\n";

        if(err.length() > 0)
            throw  new ValidationException(err);
    }
}
