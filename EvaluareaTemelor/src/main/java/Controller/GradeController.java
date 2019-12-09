package Controller;

import Entities.Grade;
import Entities.Homework;
import Entities.Student;
import Entities.YearStructure;
import Service.ServiceHomework;
import Service.ServiceStudents;
import Validation.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.events.GradeChangeEvent;
import utils.observer.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class GradeController implements Observer<GradeChangeEvent> {

    ObservableList<Grade> model = FXCollections.observableArrayList();

    @FXML
    private TextField GradeTextField;

    @FXML
    private TableView<Grade> table;

    @FXML
    private TextArea feedbackTextArea;

    @FXML
    private CheckBox motivatCheckBox;

    @FXML
    private CheckBox lateCheckBox;

    @FXML
    private ComboBox<String> homeworkBox;

    @FXML
    private Button studentButtonChange, addButton;

    @FXML
    private Button homeworkButtonChange;
    private int late = 0;
    private ServiceStudents serviceStudents;
    private ServiceHomework srvHomework;
    private Controller c;
    private Student st;

    public void setAll(ServiceStudents serviceStudents, ServiceHomework srvHomework, Controller c, Student st){
        this.serviceStudents = serviceStudents;
        this.srvHomework = srvHomework;
        this.c = c;
        this.st = st;
        this.c.addObserver(this);
        initAll();
    }
    @FXML
    public void initialize(){
        table.setItems(model);

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

        addButton.setOnAction(e ->{
            String abc = homeworkBox.getValue();
            String[] arr = abc.split("\\.");
            String gradeOne= GradeTextField.getText();
            try {
                double gradeTwo = Double.parseDouble(gradeOne);
                boolean fal = c.saveGr(st.getId(), Integer.parseInt(arr[0]),st.getCadruDidacticIndrumatorLab(), gradeTwo, feedbackTextArea.getText(), late);
                if(fal == true)
                    Message.showMessage(null, Alert.AlertType.INFORMATION, "Salvare", "Nota a fost salvata cu succes.");
                else{
                    Message.showErrorMessage(null,  "Nota nu a putut fi salvata.");
                }
            } catch (NumberFormatException | ValidationException ex) {
                Message.showErrorMessage(null, ex.getMessage());
            }
        });

        homeworkBox.setOnAction(e ->{
            motivatCheckBox.setSelected(false);
            lateCheckBox.setSelected(false);
            String abc = homeworkBox.getValue();
            if (abc != null) {
                String[] arr = abc.split("\\.");
                Homework home = c.findHm(Integer.parseInt(arr[0]));
                scurt(home);

                motivatCheckBox.setOnAction(event -> {
                    if (motivatCheckBox.isSelected()) {
                        feedbackTextArea.setText("");
                        addButton.setDisable(false);
                        late = 1;
                    } else {
                        if (home.getDeadlineWeek() < YearStructure.getInstance().getCurrentWeek() - 2 && !lateCheckBox.isSelected()) {
                            scurt(home);
                            addButton.setDisable(true);
                            late = 0;
                        }
                    }
                });

                lateCheckBox.setOnAction(event -> {
                    if (lateCheckBox.isSelected()) {
                        feedbackTextArea.setText("");
                        addButton.setDisable(false);
                        late = 1;
                    } else {
                        if (home.getDeadlineWeek() < YearStructure.getInstance().getCurrentWeek() - 2 && !motivatCheckBox.isSelected()) {
                            scurt(home);
                            addButton.setDisable(true);
                            late = 0;
                        }
                    }
                });
            }
        });
    }

    private void initAll(){
        Iterable<Homework> s = c.getAllHomeworks();
        List<Homework> homework = StreamSupport.stream(s.spliterator(), false)
                .collect(Collectors.toList());
        List<String> a = new ArrayList<>();
        for(Homework h: homework)
            a.add(Integer.toString(h.getId()) + "." + h.getDescription());
        Collections.sort(a, Collections.reverseOrder());
        ObservableList<String> list = FXCollections.observableList(a);
        homeworkBox.setItems(list);

        Iterable<Grade> s1 = c.getAllGrades();
        List<Grade> grade = StreamSupport.stream(s1.spliterator(), false)
                .collect(Collectors.toList());

        List<Grade> afis = new ArrayList<>();
        for(Grade all:grade)
            if(all.getStudent().getId() == st.getId() && all != null)
                afis.add(all);
        model.setAll(afis);
    }

    private void scurt(Homework home){
        if (home.getDeadlineWeek() == YearStructure.getInstance().getCurrentWeek() - 1 ||
                home.getDeadlineWeek() == YearStructure.getInstance().getCurrentWeek() - 2){
            int nota = home.getDeadlineWeek() - YearStructure.getInstance().getCurrentWeek();
            String scris = "NOTA A FOST DIMINUATA CU " + nota + " PUNCTE DATORITA INTARZIERILOR";
            feedbackTextArea.setText(scris);
            addButton.setDisable(false);
        }

            if(home.getDeadlineWeek() < YearStructure.getInstance().getCurrentWeek() - 2){
                String scris = "TEMA NU MAI POATE FI PREDATA";
                feedbackTextArea.setText(scris);
                addButton.setDisable(true);
            }
        if(home.getDeadlineWeek() >= YearStructure.getInstance().getCurrentWeek()) {
            addButton.setDisable(false);
            feedbackTextArea.setText("");
        }
    }


    @Override
    public void update(GradeChangeEvent gradeChangeEvent) {
        initAll();
    }
}
