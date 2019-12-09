package utils.events;

import Entities.Grade;
import Entities.Homework;

public class GradeChangeEvent implements Event {
    private ChangeEventType type;
    private Grade data, oldData;

    public GradeChangeEvent(ChangeEventType type, Grade data){
        this.type=type;
        this.data=data;
    }
    public GradeChangeEvent(ChangeEventType type, Grade data, Grade oldData){
        this.type=type;
        this.data=data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Grade getData() {
        return data;
    }

    public Grade getOldData() {
        return oldData;
    }
}
