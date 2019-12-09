package console;

import Controller.Controller;
import Entities.Grade;
import Entities.Homework;
import Entities.Student;
import Validation.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UI {
    private Controller c;
    private Scanner keyboard;

    public UI(Controller c){
        this.c = c;
        this.keyboard = new Scanner(System.in);
    }

    void changeStudent(){
        System.out.println("ID Nume Prenume grupa email numeProfesor");
        String[] all = keyboard.nextLine().split(" ");
        if(all.length == 6){
            boolean a = false;
            try {
                a = c.updateSt(Integer.parseInt(all[0]), all[1], all[2], Integer.parseInt(all[3]), all[4], all[5]);
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
            if(a == true)
                System.out.println("Studentul modificat cu success.");
            else
                System.out.println("Studentul nu a fost modificat.");
        }
        else{
            System.out.println("Numar elemente invalide.");
        }
    }

    void addStudent(){
        System.out.println("ID Nume Prenume grupa email numeProfesor");
        String[] all = keyboard.nextLine().split(" ");
        if(all.length == 6){
            boolean a = false;
            try {
                a = c.saveSt(Integer.parseInt(all[0]), all[1], all[2], Integer.parseInt(all[3]), all[4], all[5]);
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
            if(a == true)
                System.out.println("Studentul adaugat cu success.");
            else
                System.out.println("Studentul nu a fost adaugat.");
        }
        else{
            System.out.println("Numar elemente invalide.");
        }
    }

    void deleteOneStudent(){
        System.out.println("ID");
        Integer i = Integer.parseInt(keyboard.nextLine());
        boolean a = c.deleteSt(i);
        if (a == true)
            System.out.println("Studentul a fost sters cu succes.");
        else
            System.out.println("Studentul nu a fost gasit ori nu poate fi sters.");
    }
    void printOneStudent(){
        System.out.println("ID");
        Integer i = Integer.parseInt(keyboard.nextLine());
        Student a = c.findSt(i);
        if(a == null)
            System.out.println("Studentul nu a fost gasit.");
        else
            System.out.println(a);
    }
    void printAllStudents(){
        Iterable<Student> it = c.getAllStudents();
        it.forEach(System.out::println);
    }
    void printMenuStudent(){
        System.out.println("0. Back");
        System.out.println("1. add student");
        System.out.println("2. delete student");
        System.out.println("3. modify student");
        System.out.println("4. find one student");
        System.out.println("5. print all students");
    }
    void printStudent(){
        Integer option = -1;
        while(option != 0){
            printMenuStudent();
            option = Integer.parseInt(keyboard.nextLine());
            switch (option){
                case 1: addStudent(); break;
                case 2: deleteOneStudent(); break;
                case 3: changeStudent(); break;
                case 4: printOneStudent(); break;
                case 5: printAllStudents(); break;
                case 0: break;
                default:
                    System.out.println("Invalid option."); break;
            }
        }
    }

    /**
     * UI for homework
     */
    void changeHomework(){
        System.out.println("ID description deadlineWeek");
        String[] all = keyboard.nextLine().split(" ");
        if(all.length == 3){
            boolean a = false;
            try {
                a = c.updateHm(Integer.parseInt(all[0]), all[1], Integer.parseInt(all[2]));
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
            if(a == true)
                System.out.println("Tema modificata cu success.");
            else
                System.out.println("Tema nu a fost modificata.");
        }
        else{
            System.out.println("Numar elemente invalide.");
        }
    }

    void addHomework(){
        System.out.println("ID description deadlineWeek");
        String[] all = keyboard.nextLine().split(" ");
        if(all.length == 3){
            boolean a = false;
            try {
                a = c.saveHm(Integer.parseInt(all[0]), all[1], Integer.parseInt(all[2]));
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
            if(a == true)
                System.out.println("Tema adaugata cu success.");
            else
                System.out.println("Tema nu a fost adaugata.");
        }
        else{
            System.out.println("Numar elemente invalide.");
        }
    }

    void deleteOneHomework(){
        System.out.println("ID");
        Integer i = Integer.parseInt(keyboard.nextLine());
        boolean a = c.deleteHm(i);
        if (a == true)
            System.out.println("Tema a fost stearsa cu succes.");
        else
            System.out.println("Homework nu a fost gasita ori nu poate fi stearsa.");
    }
    void printOneHomework(){
        System.out.println("ID");
        Integer i = Integer.parseInt(keyboard.nextLine());
        Homework a = c.findHm(i);
        if(a == null)
            System.out.println("Tema nu a fost gasita.");
        else
            System.out.println(a);
    }
    void printAllHomeworks(){
        Iterable<Homework> it = c.getAllHomeworks();
        it.forEach(System.out::println);
    }
    void printMenuHomework(){
        System.out.println("0. Back");
        System.out.println("1. add homework");
        System.out.println("2. delete homework");
        System.out.println("3. modify homework");
        System.out.println("4. find one homework");
        System.out.println("5. print all homeworks");
    }
    void printHomeworks(){
        Integer option = -1;
        while(option != 0){
            printMenuHomework();
            option = Integer.parseInt(keyboard.nextLine());
            switch (option){
                case 1: addHomework(); break;
                case 2: deleteOneHomework(); break;
                case 3: changeHomework(); break;
                case 4: printOneHomework(); break;
                case 5: printAllHomeworks(); break;
                case 0: break;
                default:
                    System.out.println("Invalid option."); break;
            }
        }
    }

    /**
     * UI for grades
     */

    void changeGrade(){
        System.out.println("IdStudent IdTema professor grade");
        String[] all = keyboard.nextLine().split(" ");
        System.out.println("feedback");
        String ab = keyboard.nextLine();
        if(all.length == 4){
            boolean a = false;
            try {
                a = c.updateGr(Integer.parseInt(all[0]), Integer.parseInt(all[1]), all[2], Double.parseDouble(all[3]), ab);
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
            if(a == true)
                System.out.println("Nota modificata cu success.");
            else
                System.out.println("Nota nu a fost modificata.");
        }
        else{
            System.out.println("Numar elemente invalide.");
        }
    }

    void addGrade(){
        System.out.println("IdStudent IdTema professor grade");
        String[] all = keyboard.nextLine().split(" ");
        System.out.println("feedback");
        String ab = keyboard.nextLine();

        System.out.println("Profesorul a intarziat? 0:NU 1:DA ");
        String lateGraded = keyboard.nextLine();
        Integer late = 0;
        if (Integer.parseInt(lateGraded) == 1)
            late = 1;

        System.out.println("Studentul este motivat? 0:NU 1:DA ");
        lateGraded = keyboard.nextLine();
        if (Integer.parseInt(lateGraded) == 1)
            late = 1;
        if(all.length == 4){
            boolean a = false;
            try {
                a = c.saveGr(Integer.parseInt(all[0]), Integer.parseInt(all[1]), all[2], Double.parseDouble(all[3]), ab, late);
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
            if(a == true)
                System.out.println("Nota adaugata cu success.");
            else
                System.out.println("Nota nu a fost adaugata.");
        }
        else{
            System.out.println("Numar elemente invalide.");
        }
    }

    void deleteOneGrade(){
        System.out.println("IdStudent IdTema");
        String[] all = keyboard.nextLine().split(" ");
        boolean a = c.deleteGr(Integer.parseInt(all[0]), Integer.parseInt(all[1]));
        if (a == true)
            System.out.println("Nota a fost stearsa cu succes.");
        else
            System.out.println("Nota nu a fost gasita ori nu poate fi stearsa.");
    }
    void printOneGrade(){
        System.out.println("IdStudent IdTema");
        String[] all = keyboard.nextLine().split(" ");
        Grade a = c.findGr(Integer.parseInt(all[0]), Integer.parseInt(all[1]));
        if(a == null)
            System.out.println("Nota nu a fost gasita.");
        else
            System.out.println(a);
    }
    void printAllGrades(){
        Iterable<Grade> it = c.getAllGrades();
        it.forEach(System.out::println);
    }
    void printMenuGrades(){
        System.out.println("0. Back");
        System.out.println("1. add grade");
        System.out.println("2. delete grade");
        System.out.println("3. modify grade");
        System.out.println("4. find one grade");
        System.out.println("5. print all grades");
    }
    void printGrades(){
        Integer option = -1;
        while(option != 0){
            printMenuGrades();
            option = Integer.parseInt(keyboard.nextLine());
            switch (option){
                case 1: addGrade(); break;
                case 2: deleteOneGrade(); break;
                case 3: changeGrade(); break;
                case 4: printOneGrade(); break;
                case 5: printAllGrades(); break;
                case 0: break;
                default:
                    System.out.println("Invalid option."); break;
            }
        }
    }

    void filterStudentForGroup(){
        System.out.println("Group");
        Integer group = Integer.parseInt(keyboard.nextLine());
        List<Student> it = c.getAllStudentInAGroup(group);
        it.forEach(System.out::println);
    }

    void filterStudentForHomework(){
        System.out.println("Id tema");
        Integer homework = Integer.parseInt(keyboard.nextLine());
        List<Student> it = c.getStudentsWithAHomework(homework);
        it.forEach(System.out::println);
    }

    void filterStudentForHomeworkAndProfessor(){
        System.out.println("IdTema profesor");
        String[] all = keyboard.nextLine().split(" ");
        List<Student> it = c.getAllStWithHmAndProf(Integer.parseInt(all[0]), all[1]);
        it.forEach(System.out::println);
    }

    void filterGrades(){
        System.out.println("IdTema saptamana");
        String[] all = keyboard.nextLine().split(" ");
        List<Double> it = c.getAllGradesWithAWeek(Integer.parseInt(all[0]), Integer.parseInt(all[1]));
        it.forEach(System.out::println);
    }
    void filterMenu(){
        System.out.println("0. Back");
        System.out.println("1. Filtrarea a toti studentii unei grupe.");
        System.out.println("2. Filtrarea a toti studentii care au predat o tema.");
        System.out.println("3. Filtrarea a toti studentii care au predat o tema unui anumit profesor.");
        System.out.println("4. Toate notele a unei teme dintr-o saptamana");
    }
    void filter(){
        Integer option = -1;
        while(option != 0){
            filterMenu();
            option = Integer.parseInt(keyboard.nextLine());
            switch (option){
                case 1: filterStudentForGroup(); break;
                case 2: filterStudentForHomework(); break;
                case 3: filterStudentForHomeworkAndProfessor(); break;
                case 4: filterGrades();break;
                case 0: break;
                default:
                    System.out.println("Invalid option."); break;
            }
        }
    }
    public void printMenu(){
        System.out.println("0. Exit");
        System.out.println("1. Student");
        System.out.println("2. Homework");
        System.out.println("3. Grade");
        System.out.println("4. Filter");
    }

    public void Run(){
        Integer option = -1;
        while(option != 0){
            printMenu();
            option = Integer.parseInt(keyboard.nextLine());
            switch (option){
                case 1: printStudent(); break;
                case 2: printHomeworks(); break;
                case 3: printGrades(); break;
                case 4: filter(); break;
                case 0: break;
                default:
                    System.out.println("Invalid option."); break;
            }
        }
    }
}
