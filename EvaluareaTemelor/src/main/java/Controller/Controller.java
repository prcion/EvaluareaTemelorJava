package Controller;

import Entities.Grade;
import Entities.Homework;
import Entities.Student;
import Entities.YearStructure;
import Service.ServiceGrade;
import Service.ServiceHomework;
import Service.ServiceStudents;
import Validation.ValidationException;
import utils.events.ChangeEventType;
import utils.events.GradeChangeEvent;
import utils.events.HomeworkChangeEvent;
import utils.observer.Observable;
import utils.observer.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Controller implements Observable<GradeChangeEvent> {
    private ServiceStudents srvSt;
    private ServiceHomework srvHm;
    private ServiceGrade srvGr;

    public Controller(ServiceStudents srvSt, ServiceHomework srvHm, ServiceGrade srvGr){
        this.srvSt = srvSt;
        this.srvHm = srvHm;
        this.srvGr = srvGr;
    }

    /**
     * Operation for students service
     */
    public boolean saveSt(Integer id, String firstName, String lastName,
                               Integer group, String email, String professor) throws ValidationException {
        return srvSt.saveStudent(id, firstName, lastName, group, email, professor);
    }

    public boolean deleteSt(Integer id){
        return srvSt.deleteStudent(id);
    }

    public boolean updateSt(Integer id, String firstName, String lastName,
                                 Integer group, String email, String professor) throws ValidationException {
        return srvSt.updateStudent(id, firstName, lastName, group, email, professor);
    }

    public Student findSt(Integer id){
        return srvSt.findStudent(id);
    }

    public Iterable<Student> getAllStudents(){
        return srvSt.getAll();
    }

    /**
     * Operation for homework service
     */
    public boolean saveHm(Integer id, String description, Integer deadlineWeek) throws ValidationException {
        return srvHm.saveHomework(id, description, deadlineWeek);

    }

    public boolean deleteHm(Integer id){
        return srvHm.deleteHomework(id);
    }

    public boolean updateHm(Integer id, String description, Integer deadlineWeek) throws ValidationException {
        return srvHm.updateHomework(id, description, deadlineWeek);
    }

    public Homework findHm(Integer id){
        return srvHm.findHomework(id);
    }

    public Iterable<Homework> getAllHomeworks(){
        return srvHm.getAll();
    }

    /**
     * Operation for grades service
     */

    public boolean saveGr(Integer idStudent, Integer idHomework, String professor, double grade, String feedback, Integer late) throws ValidationException {
        Student st = findSt(idStudent);
        Homework hm = findHm(idHomework);
        if(st == null || hm == null) {
            return false;
        }
        Grade verif =srvGr.saveGrade(st, hm, YearStructure.getInstance().getCurrentWeek(), professor, grade, feedback, late);
        if(verif == null) {
            notifyObservers(new GradeChangeEvent(ChangeEventType.ADD, verif));
            return true;
        }
        return  false;
    }

    public boolean deleteGr(Integer idStudent, Integer idHomework){
        Student st = findSt(idStudent);
        Homework hm = findHm(idHomework);
        return srvGr.deleteGrade(st, hm);
    }


    public Grade findGr(Integer idStudent, Integer idHomework){
        Student st = findSt(idStudent);
        Homework hm = findHm(idHomework);
        return srvGr.findGrade(st, hm);
    }

    public boolean updateGr(Integer idStudent, Integer idHomework, String professor,
                            double grade, String feedback) throws ValidationException {
        Student st = findSt(idStudent);
        Homework hm = findHm(idHomework);
        Grade gr = findGr(idStudent,idHomework);
        if(st == null || hm == null)
            return false;
        return srvGr.updateGrade(st, hm, gr.getWeek(), professor, grade, feedback);
    }

    public Iterable<Grade> getAllGrades(){
        return srvGr.getAll();
    }


    public <E> List<E> makeCollection(Iterable<E> it){
        List<E> lst = new ArrayList<>();
        it.forEach(x -> lst.add(x));
        return lst;
    }

    public List<Student> getAllStudentInAGroup(Integer group){

        return makeCollection(srvSt.getAll()).stream()
                .filter(x -> x.getGrupa().equals(group))
                .collect(Collectors.toList());
    }

    public List<Student> getStudentsWithAHomework(Integer idHomework){

        return makeCollection(srvGr.getAll()).stream()
                .filter(x -> x.getHomework().getId().equals(idHomework))
                .map(Grade::getStudent)
                .collect(Collectors.toList());
    }

    public List<Student> getAllStWithHmAndProf(Integer idHomework, String professor){

        return makeCollection(srvGr.getAll()).stream()
                .filter(x -> x.getHomework().getId().equals(idHomework) && x.getProfessor().equals(professor))
                .map(Grade::getStudent)
                .collect(Collectors.toList());
    }

    public List<Double> getAllGradesWithAWeek(Integer idHomework, Integer week){

        return makeCollection(srvGr.getAll()).stream()
                .filter(x -> x.getHomework().getId().equals(idHomework) && x.getWeek().equals(week))
                .map(Grade::getGrade)
                .collect(Collectors.toList());
    }

    private List<Observer<GradeChangeEvent>> observers=new ArrayList<>();
    @Override
    public void addObserver(Observer<GradeChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<GradeChangeEvent> e) {

    }

    @Override
    public void notifyObservers(GradeChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }

    public void raport1(){
        Iterable<Student> s1 = getAllStudents();
        List<Student> student = StreamSupport.stream(s1.spliterator(), false)
                .collect(Collectors.toList());

        Iterable<Grade> s = getAllGrades();
        List<Grade> grade = StreamSupport.stream(s.spliterator(), false)
                .collect(Collectors.toList());

        for (Student st :student){
            double media1 = 0;
            int pond = 0;
            for(Grade gr: grade){
                if(st.getId() == gr.getStudent().getId()){
                    double nota = gr.getGrade();
                    Homework hm = gr.getHomework();
                    int pondere = hm.getDeadlineWeek() - hm.getStartWeek();
                    media1 += pondere * nota;
                    pond += pondere;
                }
            }
            st.setMedie(media1 / pond);
        }
    }

    public void raport2(){

        Iterable<Grade> s = getAllGrades();
        List<Grade> grade = StreamSupport.stream(s.spliterator(), false)
                .collect(Collectors.toList());

        for (Grade gr:grade){
            double media = gr.getHomework().getMedia();
            media += gr.getGrade();
            gr.getHomework().setMedia(media);
        }
    }

}
