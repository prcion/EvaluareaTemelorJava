package Entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class YearStructure {

    public class Semester
    {
        private LocalDate startSemester;
        private LocalDate endSemester;
        private LocalDate startHoliday;
        private LocalDate endHoliday;

        public Semester(String startSemester, String endSemester)
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");

            this.startSemester = LocalDate.parse(startSemester, formatter);
            this.endSemester = LocalDate.parse(endSemester, formatter);
        }

        public void addHoliday(String startHoliday, String endHoliday)
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
            this.startHoliday = LocalDate.parse(startHoliday, formatter);
            this.endHoliday = LocalDate.parse(endHoliday, formatter);
        }

        public boolean isVacantion()
        {
            LocalDate localNowDate = LocalDate.now();

            if (!localNowDate.isBefore(startHoliday)
                    && !localNowDate.isAfter(endHoliday))
                return true;
            return false;
        }

        public boolean isEnded()
        {
            return false;
        }

        public Integer getWeek()
        {
            Integer week = 0;
            LocalDate start_sem = startSemester;
            while(start_sem.isBefore(LocalDate.now()) && endSemester.isAfter(LocalDate.now()))
            {
                week = week + 1;
                start_sem = start_sem.plusDays(7);
            }

            return week;
        }
    }

    private Semester Semester1;
    private Semester Semester2;

    public YearStructure()
    {
        Semester1 = new Semester("30.09.2019", "19.01.2020");
        Semester2 = new Semester("24.02.2020", "7.06.2020");

        Semester1.addHoliday("23.12.2019", "05.01.2020");
        Semester2.addHoliday("20.04.2020", "26.04.2020");

    }

    public Integer getSemester()
    {
        if (Semester1.isEnded() == false)
            return 1;
        return 2;
    }

    public Integer getCurrentWeek()
    {
        if (Semester1.isEnded() == false)
            return Semester1.getWeek() + 1;
        if (Semester2.isEnded() == false)
            return Semester2.getWeek() + 1;

        return -1;
    }

    private static YearStructure instance = new YearStructure();
    public static YearStructure getInstance()
    {
        return instance;
    }
}