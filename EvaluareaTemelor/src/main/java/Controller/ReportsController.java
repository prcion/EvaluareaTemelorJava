package Controller;

import Entities.Homework;
import Entities.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ReportsController {

    ObservableList<Student> model = FXCollections.observableArrayList();

    @FXML
    private Button Raport1;

    @FXML
    private TableView<Student> table;

    @FXML
    private Button Raport2;

    @FXML
    private Button Raport3;

    @FXML
    private Button Raport4;

    @FXML
    private TextField TemaTextField;

    @FXML
    private Button studentButtonChange;

    @FXML
    private Button homeworkButtonChange;
    private Controller c;
    public void setAll(Controller c){
        this.c = c;
        TemaTextField.setVisible(false);
    }

    @FXML public void initialize(){
        table.setItems(model);
        Raport1.setOnAction(e ->{
            TemaTextField.setVisible(false);
            c.raport1();
            Iterable<Student> s = c.getAllStudents();
            List<Student> students = StreamSupport.stream(s.spliterator(), false)
                    .collect(Collectors.toList());
            model.setAll(students);
        });

        Raport3.setOnAction(e ->{
            TemaTextField.setVisible(false);
            c.raport1();
            Iterable<Student> s = c.getAllStudents();
            List<Student> students = StreamSupport.stream(s.spliterator(), false)
                    .collect(Collectors.toList());
            List<Student> listF = new ArrayList<>();

            for(Student st: students){
                if (st.getNota() >= 4)
                    listF.add(st);
            }
            model.setAll(listF);
        });

        Raport4.setOnAction(e ->{
            TemaTextField.setVisible(false);
            c.raport1();
            Iterable<Student> s = c.getAllStudents();
            List<Student> students = StreamSupport.stream(s.spliterator(), false)
                    .collect(Collectors.toList());
            List<Student> listF = new ArrayList<>();

            for(Student st: students){
                if (st.getNota() == 10)
                    listF.add(st);
            }
            model.setAll(listF);
        });

        Raport2.setOnAction(e ->{
            List<Student> lst = new ArrayList<>();
            model.setAll(lst);
            TemaTextField.setVisible(true);
            TemaTextField.setDisable(true);
            c.raport2();
            Iterable<Homework> s = c.getAllHomeworks();
            List<Homework> hm = StreamSupport.stream(s.spliterator(), false)
                    .collect(Collectors.toList());

            double media = 0;
            for(Homework h: hm){
                if (media < h.getMedia()) {
                    media = h.getMedia();
                }
            }
            for(Homework h: hm){
                if (media == h.getMedia()) {
                    TemaTextField.setText("Tema cea mai grea este " + "ID "  + Integer.toString(h.getId()) +
                            " Descriere " + h.getDescription());
                }
            }
        });
    }
}
