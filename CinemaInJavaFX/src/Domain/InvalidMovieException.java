package Domain;

public class InvalidMovieException extends RuntimeException {

    public InvalidMovieException(String message){
        super(message);
    }

}
