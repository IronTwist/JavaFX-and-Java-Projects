package Domain;

public class ReservationNumberPerMovieViewModel {
        String movieTitle;
        int numberOfReservation;

    public ReservationNumberPerMovieViewModel(String movieTitle, int numberOfreservation) {
        this.movieTitle = movieTitle;
        this.numberOfReservation = numberOfreservation;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getNumberOfReservation() {
        return numberOfReservation;
    }

    public void setNumberOfReservation(int numberOfReservation) {
        this.numberOfReservation = numberOfReservation;
    }

    @Override
    public String toString() {
        return "ReservationNumberPerMovieViewModel{" +
                "movieTitle='" + movieTitle + '\'' +
                ", numberOfReservation=" + numberOfReservation +
                '}';
    }
}
