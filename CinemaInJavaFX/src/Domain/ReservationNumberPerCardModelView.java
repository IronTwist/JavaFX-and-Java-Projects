package Domain;

public class ReservationNumberPerCardModelView {

    int cardNumber;
    int numberOfReservations;

    public ReservationNumberPerCardModelView(int cardNumber, int numberOfReservations) {
        this.cardNumber = cardNumber;
        this.numberOfReservations = numberOfReservations;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getNumberOfReservations() {
        return numberOfReservations;
    }

    public void setNumberOfReservations(int numberOfReservations) {
        this.numberOfReservations = numberOfReservations;
    }

    @Override
    public String toString() {
        return "ReservationNumberPerCardModelView{" +
                "cardNumber=" + cardNumber +
                ", numberOfReservations=" + numberOfReservations +
                '}';
    }
}
