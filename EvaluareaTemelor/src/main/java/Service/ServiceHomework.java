package Service;

import Entities.Homework;
import Entities.Student;
import Repository.HomeworkXMLRepository;
import Repository.MemoryRepository;
import Validation.ValidationException;
import utils.events.ChangeEventType;
import utils.events.HomeworkChangeEvent;
import utils.events.StudentChangeEvent;
import utils.observer.Observable;
import utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class ServiceHomework implements Observable<HomeworkChangeEvent> {
    HomeworkXMLRepository repo;

    public ServiceHomework(HomeworkXMLRepository repo){
        this.repo = repo;
    }

    public boolean saveHomework(Integer id, String description, Integer deadlineWeek) throws ValidationException {
        Homework s = new Homework(id, description, deadlineWeek);
        Homework verif = repo.save(s);
        if(verif == null) {
            notifyObservers(new HomeworkChangeEvent(ChangeEventType.ADD, verif));
            return true;
        }
        return  false;
    }

    public boolean deleteHomework(Integer id){
        Homework verif = repo.delete(id);
        if(verif != null) {
            notifyObservers(new HomeworkChangeEvent(ChangeEventType.DELETE, verif));
            return true;
        }
        return  false;
    }

    public boolean updateHomework(Integer id, String description, Integer deadlineWeek) throws ValidationException {
        Homework s = new Homework(id, description, deadlineWeek);
        Homework verif = repo.update(s);
        if(verif == null) {
            notifyObservers(new HomeworkChangeEvent(ChangeEventType.UPDATE, verif));
            return true;
        }
        return  false;
    }

    public Homework findHomework(Integer id){
        return repo.findOne(id);
    }

    public Iterable<Homework> getAll(){
        return repo.findAll();
    }

    private List<Observer<HomeworkChangeEvent>> observers=new ArrayList<>();
    @Override
    public void addObserver(Observer<HomeworkChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<HomeworkChangeEvent> e) {

    }

    @Override
    public void notifyObservers(HomeworkChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }
}
