package Domain;

public class MovieValidator {

    public void validate(Movie movie){
        String message = "";

        if(movie.getId() < -1){
            message += "Id must be positive!\n";
        }

        if(movie.getPrice() <= 0){
            message += "The price must be greater than 0!\n";
        }

        if(movie.getMovieReleaseYear() <= 0){
            message += "Release year must be greater than 0!";
        }

        if(movie.getMovieReleaseYear() > new DateTime().getDate().getYear()){
            message += "Release year is bigger than current year";
        }

        if(!message.equals("")){
            throw new InvalidMovieException(message);
        }
    }
}
