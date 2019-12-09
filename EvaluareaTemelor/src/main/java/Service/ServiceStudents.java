package Service;

import Entities.Student;
import Repository.MemoryRepository;
import Repository.StudentXMLRepository;
import Validation.ValidationException;
import utils.events.ChangeEventType;
import utils.events.StudentChangeEvent;
import utils.observer.Observable;
import utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class ServiceStudents implements Observable<StudentChangeEvent> {
    private StudentXMLRepository repo;

    public ServiceStudents(StudentXMLRepository repo){
        this.repo = repo;
    }

    public boolean saveStudent(Integer id, String firstName, String lastName, Integer group, String email, String proffesor) throws ValidationException {
        Student s = new Student(id, firstName, lastName, group, email, proffesor);
        Student verif = repo.save(s);
        if(verif == null) {
            notifyObservers(new StudentChangeEvent(ChangeEventType.ADD, verif));
            return true;
        }
        return  false;
    }

    public boolean deleteStudent(Integer id){
        Student verif = repo.delete(id);
        if(verif != null)
        {
            notifyObservers(new StudentChangeEvent(ChangeEventType.DELETE, verif));
            return true;
        }
        return  false;
    }

    public boolean updateStudent(Integer id, String firstName, String lastName, Integer group, String email, String professor) throws ValidationException {
        Student s = new Student(id, firstName, lastName, group, email, professor);
        Student verif = repo.update(s);
        if(verif == null) {
            notifyObservers(new StudentChangeEvent(ChangeEventType.UPDATE, verif));
            return true;
        }
        return  false;
    }

    public Student findStudent(Integer id){
        return repo.findOne(id);
    }

    public Iterable<Student> getAll(){
        return repo.findAll();
    }

    private List<Observer<StudentChangeEvent>> observers=new ArrayList<>();
    @Override
    public void addObserver(Observer<StudentChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<StudentChangeEvent> e) {

    }

    @Override
    public void notifyObservers(StudentChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }
}
