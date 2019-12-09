package Entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateCurent {
    private int dateCurent = 0;
    public void date(){
        LocalDate startDate = LocalDate.of(2019, Month.SEPTEMBER, 30);
        String endDate = LocalDate.now().format((DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        String[] l = endDate.split("-");
        LocalDate ceva= LocalDate.of(Integer.parseInt(l[2]), Integer.parseInt(l[1]), Integer.parseInt(l[0]));

        long weeksInYear = ChronoUnit.WEEKS.between(startDate, ceva) +1;
        dateCurent =(int) weeksInYear;
    }

    public int getDate(){
        return dateCurent;
    }

}
