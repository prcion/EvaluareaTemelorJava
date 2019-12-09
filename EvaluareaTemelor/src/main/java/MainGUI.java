import Controller.StudentController;
import Controller.Controller;
import Repository.GradeXMLRepository;
import Repository.HomeworkXMLRepository;
import Repository.StudentXMLRepository;
import Service.ServiceGrade;
import Service.ServiceHomework;
import Service.ServiceStudents;
import Validation.ValidationNota;
import Validation.ValidationStudents;
import Validation.ValidationTema;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGUI extends Application {

    private ServiceStudents srvStd;
    private StudentXMLRepository repo;
    private ServiceHomework srvHm;
    private Controller c;
    @Override
    public void start(Stage primaryStage) throws IOException{

        StudentXMLRepository ms = new StudentXMLRepository(new ValidationStudents(), "E:\\EvaluareaTemelor\\src\\main\\resources\\StudentXML.tld");
        srvStd = new ServiceStudents(ms);

        HomeworkXMLRepository hm = new HomeworkXMLRepository(new ValidationTema(), "E:\\EvaluareaTemelor\\src\\test\\resources\\ExportedGrades\\HomeworkXML.tld");
        srvHm = new ServiceHomework(hm);

        GradeXMLRepository gr = new GradeXMLRepository(new ValidationNota(), "E:\\EvaluareaTemelor\\src\\test\\resources\\ExportedGrades\\GradeXML.tld");
        ServiceGrade srvGr = new ServiceGrade(gr, "E:\\EvaluareaTemelor\\src\\test\\resources\\ExportedGrades\\");

        c = new Controller(srvStd, srvHm, srvGr);

        primaryStage.setTitle("Student");
        init(primaryStage);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void init(Stage primaryStage) throws IOException {
        FXMLLoader studentLoader = new FXMLLoader();
        studentLoader.setLocation(getClass().getResource("studentView.fxml"));

        AnchorPane studentLayot = studentLoader.load();
        primaryStage.setScene(new Scene(studentLayot));

        StudentController studentController = studentLoader.getController();
        studentController.setService(srvStd, srvHm, c);
    }
}
