package Domain.Validators;

import Domain.Exceptions.InvalidReservationException;
import Domain.Reservation;

public class ReservationValidator {

    public void validate(Reservation reservation){
        String message = "";

        if(reservation.getId() < 0){
            message += "Id must be greater positive!\n";
        }

        if(reservation.getClientNumberCard() <= 0){
            message += "Client card must be positive!\n";
        }

        if(reservation.getMovieId() < 0){
            message += "Movie id does not exist!";
        }

        if(!message.equals("")){
            throw new InvalidReservationException(message);
        }
    }
}


