package Controller;

import Entities.Homework;
import Entities.Student;
import Service.ServiceHomework;
import Service.ServiceStudents;
import Validation.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.events.HomeworkChangeEvent;
import utils.events.StudentChangeEvent;
import utils.observer.Observer;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class HomeworkController implements Observer<HomeworkChangeEvent> {

    ObservableList<Homework> model = FXCollections.observableArrayList();
    @FXML
    private TextField IDTextField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField deadlineWeekField;

    @FXML
    private TableView<Homework> table;

    @FXML
    private TextField startWeekField;

    @FXML
    private Button studentButtonChange;
    private ServiceStudents serviceStudents;
    private ServiceHomework srvHomework;
    private Controller c;
    public void setSt(ServiceStudents serviceStudents, ServiceHomework srvHomework, Controller c){
        this.serviceStudents = serviceStudents;
        this.srvHomework = srvHomework;
        this.c = c;
        this.srvHomework.addObserver(this);
        initAll();
    }
    @FXML
    void initialize(){
        table.setItems(model);
        table.getSelectionModel().selectedItemProperty().addListener((observableValue, homework, t1) -> loadTextFields(t1));

        studentButtonChange.setOnAction(event -> {
            studentButtonChange.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/studentView.fxml"));

            try {
                AnchorPane root = (AnchorPane) loader.load();
                // Create the dialog Stage.
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Student");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                //dialogStage.initOwner(primaryStage);
                Scene scene = new Scene(root);
                dialogStage.setScene(scene);

                StudentController studentController = loader.getController();
                studentController.setService(serviceStudents, srvHomework, c);
                dialogStage.show();
            } catch (IOException e) {
                Message.showErrorMessage(null, e.getMessage());
            }
        });
    }

    private void loadTextFields(Homework homework){
        if(homework==null){
            IDTextField.clear();
            descriptionField.clear();
            startWeekField.clear();
            deadlineWeekField.clear();
        }
        else{
            IDTextField.setText(Integer.toString(homework.getId()));
            descriptionField.setText(homework.getDescription());
            startWeekField.setText(Integer.toString(homework.getStartWeek()));
            deadlineWeekField.setText(Integer.toString(homework.getDeadlineWeek()));
        }
    }

    private void initAll(){
        Iterable<Homework> s = srvHomework.getAll();
        List<Homework> homework = StreamSupport.stream(s.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(homework);
    }
    @FXML
    void addHomework(ActionEvent event) {
        String id= IDTextField.getText();
        String description = descriptionField.getText();
        String deadline = deadlineWeekField.getText();

        try{
            boolean a = srvHomework.saveHomework(Integer.parseInt(id), description, Integer.parseInt(deadline));
            if(a == true)
                Message.showMessage(null, Alert.AlertType.INFORMATION, "Salvare", "Tema a fost salvata cu succes.");
            else{
                Message.showErrorMessage(null,  "Tema nu a putut fi salvata.");
            }
        } catch (ValidationException e) {
            Message.showErrorMessage(null, e.getMessage());
        }
    }

    @FXML
    void deleteHomework(ActionEvent event) {
        Homework st = (Homework) table.getSelectionModel().getSelectedItem();
        if(st != null) {
            boolean forDelete = srvHomework.deleteHomework(st.getId());
            if (forDelete == true)
                Message.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "Tema a fost stearsa cu succes.");
        } else
            Message.showErrorMessage(null, "Nu ati selectat nici o tema.");
    }

    @FXML
    void updateHomework(ActionEvent event) {
        String id= IDTextField.getText();
        String description = descriptionField.getText();
        String deadline = deadlineWeekField.getText();

        try{
            boolean a = srvHomework.updateHomework(Integer.parseInt(id), description, Integer.parseInt(deadline));
            if(a == true)
                Message.showMessage(null, Alert.AlertType.INFORMATION, "Modificare", "Tema a fost modificata cu succes.");
            else{
                Message.showErrorMessage(null,  "Tema nu a putut fi modificata.");
            }
        } catch (ValidationException e) {
            Message.showErrorMessage(null, e.getMessage());
        }
    }

    @Override
    public void update(HomeworkChangeEvent homeworkChangeEvent) {
        initAll();
    }
}
