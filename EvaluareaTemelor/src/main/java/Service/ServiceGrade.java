package Service;

import Entities.Grade;
import Entities.Homework;
import Entities.Student;
import Repository.GradeXMLRepository;
import Repository.MemoryRepository;
import Validation.ValidationException;
import utils.events.ChangeEventType;
import utils.events.GradeChangeEvent;
import utils.observer.Observable;
import utils.observer.Observer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ServiceGrade{
    GradeXMLRepository repo;
    private String fileName;
    public ServiceGrade(GradeXMLRepository repo, String fileName){
        this.repo = repo;
        this.fileName = fileName;
    }

    private void exportInformation(Grade gr){
        String fileExport = fileName+gr.getStudent().getNume()+gr.getStudent().getPrenume()+".txt";
        File f = new File(fileExport);
        try {
            f.createNewFile();
            PrintWriter pw = new PrintWriter(f);
            pw.write("Tema: "+gr.getHomework().getId().toString()+"\n"+"Nota: "+gr.getGrade()+
                    "\n"+"Predata in saptamana: "+gr.getWeek().toString()+
                    "\n"+"Deadline: "+gr.getHomework().getDeadlineWeek()+
                    "\n"+"Feedback: "+gr.getFeedback());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Grade saveGrade(Student st, Homework hm, Integer week, String professor, double grade, String feedback, Integer late) throws ValidationException {
        Grade s = new Grade(st, hm, week, professor, grade, feedback);
        s.setLate(late);
        s.setGrade(s.getMaximGrade());
        exportInformation(s);
        Grade verif = repo.save(s);
        return verif;
    }

    public boolean deleteGrade(Student st, Homework hm){
        Grade verif = repo.delete(st.getId().toString() + "." + hm.getId().toString());
        if(verif != null)
            return true;
        return  false;
    }

    public boolean updateGrade(Student st, Homework hm, Integer week, String professor, double grade, String feedback) throws ValidationException {
        Grade s = new Grade(st, hm, week, professor, grade, feedback);
        s.setGrade(s.getMaximGrade());
        Grade verif = repo.update(s);
        if(verif == null)
            return true;
        return  false;
    }

    public Grade findGrade(Student st, Homework hm){
        return repo.findOne(st.getId().toString() + "." + hm.getId().toString());
    }

    public Iterable<Grade> getAll(){
        return repo.findAll();
    }

}
