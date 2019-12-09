package Repository;

import Entities.Entity;
import Validation.ValidationException;
import Validation.Validator;

import java.util.HashMap;

public abstract class AbstractRepository<ID, E extends Entity<ID>> implements CrudRepository<ID, E> {
    private HashMap<ID, E> elems;
    private Validator<E> validator;

    public AbstractRepository(Validator<E> validator){
        this.validator = validator;
        elems = new HashMap<>();
    }


    @Override
    public E save(E elem) throws ValidationException {
        try{
            validator.validate(elem);
            if (elem == null)
                throw new IllegalArgumentException("Entitatea este invalida!!!\n");
            E el = elems.get(elem.getId());
            if(el != null) {
                throw new ValidationException("Entitatea exista!!!\n");
            }
            elems.put(elem.getId(), elem);
            return null;
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        return elem;
    }

    @Override
    public E findOne(ID id){
        if(id == null)
            throw new IllegalArgumentException("Id-ul este null");

        return elems.get(id);
    }

    @Override
    public Iterable<E> findAll(){
        return elems.values();
    }

    @Override
    public E delete(ID id){
        if(id == null)
            throw new IllegalArgumentException("Id-ul este null");

        E elem = elems.get(id);
        if(elem != null)
            elems.remove(id);
        return elem;
    }

    @Override
    public E update(E elem){
        if(elem == null)
            throw new IllegalArgumentException("Entitatea este null");
        E el = elems.get(elem.getId());
        if(el != null){
            try{
                validator.validate(elem);
                elems.put(elem.getId(),elem);
                return  null;
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        }
        return elem;
    }
}
