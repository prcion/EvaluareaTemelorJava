import Controller.Controller;
import Entities.Grade;
import Entities.Homework;
import Entities.Student;
import Repository.GradeXMLRepository;
import Repository.HomeworkXMLRepository;
import Repository.MemoryRepository;
import Repository.StudentXMLRepository;
import Service.ServiceGrade;
import Service.ServiceHomework;
import Service.ServiceStudents;
import Validation.*;
import console.UI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
public class main {
    public static  void main(String[] args){
        StudentXMLRepository ms = new StudentXMLRepository(new ValidationStudents(), "E:\\EvaluareaTemelor\\src\\test\\resources\\ExportedGrades\\StudentXML.tld");
        ServiceStudents srvSt = new ServiceStudents(ms);
        HomeworkXMLRepository hm = new HomeworkXMLRepository(new ValidationTema(), "E:\\EvaluareaTemelor\\src\\test\\resources\\ExportedGrades\\HomeworkXML.tld");
        ServiceHomework srvHm = new ServiceHomework(hm);

        GradeXMLRepository gr = new GradeXMLRepository(new ValidationNota(), "E:\\EvaluareaTemelor\\src\\test\\resources\\ExportedGrades\\GradeXML.tld");
        ServiceGrade srvGr = new ServiceGrade(gr, "E:\\EvaluareaTemelor\\src\\test\\resources\\ExportedGrades\\");

        Controller c = new Controller(srvSt, srvHm, srvGr);

        UI ui = new UI(c);
        ui.Run();
    }
}

*/
public class main{

    public static void main(String[] args){
        MainGUI.main(args);
    }

}
