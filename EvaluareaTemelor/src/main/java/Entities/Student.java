package Entities;

import Entities.Entity;

public class Student extends Entity<Integer> {
    private String nume;
    private String prenume;
    private Integer grupa;
    private String email;
    private String cadruDidacticIndrumatorLab;

    public Student(Integer ID, String nume, String prenume, Integer grupa, String email, String cadruDidacticIndrumatorLab){
        super.setId(ID);
        this.nume = nume;
        this.prenume = prenume;
        this.grupa = grupa;
        this.email = email;
        this.cadruDidacticIndrumatorLab = cadruDidacticIndrumatorLab;
    }

    private double medie;
    public void setMedie(double medie){
        this.medie = medie;
    }
    public double getNota(){
        return medie;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setGrupa(Integer grupa) {
        this.grupa = grupa;
    }

    public void setCadruDidacticIndrumatorLab(String cadruDidacticIndrumatorLab) {
        this.cadruDidacticIndrumatorLab = cadruDidacticIndrumatorLab;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public Integer getGrupa() {
        return grupa;
    }

    public String getCadruDidacticIndrumatorLab() {
        return cadruDidacticIndrumatorLab;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return getId() + " " + nume + " " + prenume + " " + grupa + " " + email + " " + cadruDidacticIndrumatorLab;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Student){
            Student s = (Student) obj;
            return s.getId() == getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
