package Entities;

import java.time.LocalDateTime;

public class Homework extends Entity<Integer> {
    private String description;
    private Integer startWeek;
    private Integer deadlineWeek;

    public Homework(){

    }
    public Homework(Integer ID, String description, Integer deadlineWeek){
        super.setId(ID);
        this.description = description;
        this.deadlineWeek = deadlineWeek;
        this.startWeek = YearStructure.getInstance().getCurrentWeek();
    }

    private double media;

    public void setMedia(double media) {
        this.media = media;
    }

    public double getMedia() {
        return media;
    }

    public Homework(Integer ID, String description, Integer startWeek, Integer deadlineWeek){
        super.setId(ID);
        this.description = description;
        this.deadlineWeek = deadlineWeek;
        this.startWeek = startWeek;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStartWeek() {
        return startWeek;
    }

    public Integer getDeadlineWeek() {
        return deadlineWeek;
    }

    public void setDeadlineWeek(Integer deadlineWeek) {
        if (YearStructure.getInstance().getCurrentWeek() <= deadlineWeek)
            this.deadlineWeek = deadlineWeek;
    }

    @Override
    public String toString() {
        return getId() + " " + description + " " + startWeek + " " + deadlineWeek;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Homework){
            Homework s = (Homework) obj;
            return s.getId() == getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
