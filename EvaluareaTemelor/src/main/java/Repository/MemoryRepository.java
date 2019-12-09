package Repository;

import Entities.Entity;
import Validation.Validator;

public class MemoryRepository<ID, E extends Entity<ID>> extends AbstractRepository<ID, E> {

    public MemoryRepository(Validator<E> validation){
        super(validation);
    }
}
