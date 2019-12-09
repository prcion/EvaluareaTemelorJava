package Controller;

import Entities.Homework;
import Entities.Student;
import Service.ServiceHomework;
import Service.ServiceStudents;
import Validation.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.events.StudentChangeEvent;
import utils.observer.Observer;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StudentController implements Observer<StudentChangeEvent> {

    ObservableList<Student> model = FXCollections.observableArrayList();

    private ServiceStudents serviceStudents;
    private ServiceHomework srvHomework;
    @FXML
    private TextField IDTextField, firstNameTextField, lastNameTextField;

    @FXML
    private TextField groupTextField, emailTextField, professorTextField;

    @FXML
    private TextField findTextField;

    @FXML
    private Button reportButtonChange;

    @FXML
    private TableView<Student> table;

    @FXML
    private Button homeworkButtonChange, addGradeButton;

    private Controller c;
    public void setService(ServiceStudents serviceStudents, ServiceHomework srvHomework, Controller c){
        this.serviceStudents = serviceStudents;
        this.srvHomework = srvHomework;
        this.c = c;
        this.serviceStudents.addObserver(this);
        initAll();
    }
    @FXML
    void initialize() {
        table.setItems(model);
        table.getSelectionModel().selectedItemProperty().addListener((observableValue, student, t1) -> loadTextFields(t1));

        FilteredList<Student> filter = new FilteredList<>(table.getItems(), x -> true);
        findTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            Predicate<Student> st = x -> x.getNume().startsWith(newValue);
            filter.setPredicate(st);
            table.setItems(filter);
        });

        addGradeButton.setOnAction(event -> {
            Student st = (Student) table.getSelectionModel().getSelectedItem();
            if(st == null) {
                Message.showErrorMessage(null, "Nu ati selectat nici un student pentru adaugarea notei.");
            }else{
            addGradeButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gradeView.fxml"));
            try {

                AnchorPane root = (AnchorPane) loader.load();
                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Nota");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                //dialogStage.initOwner(primaryStage);
                Scene scene = new Scene(root);
                dialogStage.setScene(scene);
                GradeController cont = loader.getController();
                cont.setAll(serviceStudents, srvHomework, c, st);

                dialogStage.show();

            } catch (IOException e) {
                Message.showErrorMessage(null, e.getMessage());
            }
        }});

        homeworkButtonChange.setOnAction(event -> {
            homeworkButtonChange.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/homeworkView.fxml"));
            try {
                AnchorPane root = (AnchorPane) loader.load();
                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Tema");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                //dialogStage.initOwner(primaryStage);
                Scene scene = new Scene(root);
                dialogStage.setScene(scene);


                HomeworkController homeworkController = loader.getController();
                homeworkController.setSt(serviceStudents, srvHomework, c);

                dialogStage.show();
            } catch (IOException e) {
                Message.showErrorMessage(null, e.getMessage());
            }
        });

        reportButtonChange.setOnAction(event -> {
            reportButtonChange.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/reportsView.fxml"));
            try {

                AnchorPane root = (AnchorPane) loader.load();
                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Rapoarte");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                //dialogStage.initOwner(primaryStage);
                Scene scene = new Scene(root);
                dialogStage.setScene(scene);

                ReportsController reportsController = loader.getController();
                reportsController.setAll(c);

                dialogStage.show();
            } catch (IOException e) {
                Message.showErrorMessage(null, e.getMessage());
            }
        });
    }

    private void loadTextFields(Student student){
        if(student==null){
            IDTextField.clear();
            firstNameTextField.clear();
            lastNameTextField.clear();
            groupTextField.clear();
            emailTextField.clear();
            professorTextField.clear();
        }
        else{
            IDTextField.setText(Integer.toString(student.getId()));
            firstNameTextField.setText(student.getNume());
            lastNameTextField.setText(student.getPrenume());
            groupTextField.setText(Integer.toString(student.getGrupa()));
            emailTextField.setText(student.getEmail());
            professorTextField.setText(student.getCadruDidacticIndrumatorLab());
        }
    }
    private void initAll(){
        Iterable<Student> s = serviceStudents.getAll();
        List<Student> students = StreamSupport.stream(s.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(students);
    }

    @Override
    public void update(StudentChangeEvent studentChangeEvent) {
        initAll();
    }

    @FXML
    public void addMessage(ActionEvent actionEvent) {
        String id= IDTextField.getText();
        String nume = firstNameTextField.getText();
        String prenume = lastNameTextField.getText();
        String grupa = groupTextField.getText();
        String email = emailTextField.getText();
        String teacher = professorTextField.getText();

        try{
            boolean a = serviceStudents.saveStudent(Integer.parseInt(id), nume, prenume,
                                                    Integer.parseInt(grupa), email, teacher);
            if(a == true)
                Message.showMessage(null, Alert.AlertType.INFORMATION, "Salvare", "Studentul a fost salvat cu succes.");
            else{
                Message.showErrorMessage(null,  "Studentul nu a putut fi salvat.");
            }
        } catch (ValidationException e) {
            Message.showErrorMessage(null, e.getMessage());
        }
    }

    @FXML
    public void deleteMessage(ActionEvent actionEvent) {
        Student st = (Student) table.getSelectionModel().getSelectedItem();
        if(st != null) {
            boolean forDelete = serviceStudents.deleteStudent(st.getId());
            if (forDelete == true)
                Message.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "Studentul a fost sters cu succes.");
            } else
                Message.showErrorMessage(null, "Nu ati selectat nici un student.");
    }
    @FXML
    public void updateMessage(ActionEvent actionEvent) {
        String id= IDTextField.getText();
        String nume = firstNameTextField.getText();
        String prenume = lastNameTextField.getText();
        String grupa = groupTextField.getText();
        String email = emailTextField.getText();
        String teacher = professorTextField.getText();

        try{
            boolean a = serviceStudents.updateStudent(Integer.parseInt(id), nume, prenume,
                    Integer.parseInt(grupa), email, teacher);
            if(a == true)
                Message.showMessage(null, Alert.AlertType.INFORMATION, "Modificare", "Studentul a fost modificat cu succes.");
            else{
                Message.showErrorMessage(null,  "Studentul nu a putut fi modificat.");
            }
        } catch (ValidationException e) {
            Message.showErrorMessage(null, e.getMessage());
        }
    }
}
