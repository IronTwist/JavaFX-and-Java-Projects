package Domain;

import java.util.Objects;

public class Reservation extends Entity {

    private int movieId;
    private int clientNumberCard;
    private DateTime dateTime;

    public Reservation( int movieId, int clientNumberCard, DateTime dateTime) {

        this.movieId = movieId;
        this.clientNumberCard = clientNumberCard;
        this.dateTime = dateTime;
    }

    public Reservation(int movieId, int clientNumberCard,int year,int month,int dayOfMonth,int hour,int minute) {

        this.movieId = movieId;
        this.clientNumberCard = clientNumberCard;
        this.dateTime = new DateTime(year,month,dayOfMonth,hour,minute);
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getClientNumberCard() {
        return clientNumberCard;
    }

    public void setClientNumberCard(int clientNumberCard) {
        this.clientNumberCard = clientNumberCard;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + getId() +
                ", movieId=" + movieId +
                ", clientNumberCard=" + clientNumberCard +
                ", dateTime=" + dateTime +
                '}';
    }

}
