package Entities;

import java.time.LocalDateTime;

public class Grade extends Entity<String> {
    private Integer week;
    private String professor;
    private double grade;
    private String feedback;
    private Student student;
    private Homework homework;
    private Integer late;
    @Override
    public String toString() {
        return "Grade{" +
                "week=" + week +
                ", professor='" + professor + '\'' +
                ", grade=" + grade +
                ", feedback='" + feedback + '\'' +
                ", student=" + student +
                ", homework=" + homework +
                '}';
    }
    public String getNume(){
        return student.getNume();
    }
    public String getTema(){return homework.getDescription();}
    public Grade(Student student, Homework homework, Integer week, String professor, double grade, String feedback){
        super.setId(student.getId().toString() + "." + homework.getId().toString());
        this.week = week;
        this.professor = professor;
        this.grade = grade;
        this.feedback = feedback;
        this.homework = homework;
        this.student = student;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }
    public void setLate(Integer late){
        this.late = late;
    }
    public double getMaximGrade(){
        if(late != 1) {
            Integer ar = YearStructure.getInstance().getCurrentWeek() - homework.getDeadlineWeek();
            if (ar < 1)
                return grade;
            if (ar == 1)
                return grade - 1;
            if (ar == 2)
                return grade - 2;
            return 1;
        }
        return grade;
    }
    public int hashCode() {
        return getId().hashCode();
    }
}
