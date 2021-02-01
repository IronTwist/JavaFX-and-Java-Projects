package Domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTime {

    private LocalDate date;
    private LocalTime time;

    public DateTime(){
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    public DateTime(int year,int month,int dayOfMonth){
        this.date = LocalDate.of(year,month, dayOfMonth);
        this.time = null;
    }

    public DateTime(int year,int month,int dayOfMonth, int hour, int minute){
        this.date = LocalDate.of(year,month, dayOfMonth);
        this.time = LocalTime.of(hour, minute);
    }



    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

//    @Override
//    public String toString() {
//        return "DateTime{" +
//                "date=" + date +
//                ", time=" + time +
//                '}';
//    }

    @Override
    public String toString() {
        return  date +" / " +
                 time ;

    }
}
