package Service;

import Domain.*;
import Repository.IRepository;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.List;

public class MovieService {

    private IRepository<Movie> movieIRepository;
    private MovieValidator movieValidator;
    private UndoRedoService undoRedoService;
    private IRepository<Reservation> reservationIRepository;


    public MovieService(IRepository<Movie> movieIRepository,IRepository<Reservation> reservationIRepository, UndoRedoService undoRedoService) {
        this.movieIRepository = movieIRepository;
        this.reservationIRepository = reservationIRepository;
        this.movieValidator = new MovieValidator();
        this.undoRedoService = undoRedoService;
    }

    public void addMovie(String title, int movieReleaseYear, int price, boolean inProgram){

        Movie newMovie = new Movie(title,movieReleaseYear,price,inProgram);

        this.movieValidator.validate(newMovie);
        this.movieIRepository.create(newMovie);
        undoRedoService.add(new AddOperation<Movie>(this.movieIRepository, newMovie));
    }

    public boolean undo(){
        return undoRedoService.undo();
    }
    public boolean redo(){
       return undoRedoService.redo();
    }

    public List<Movie> getAllMovies(){
        return this.movieIRepository.read();
    }

    public void deleteMovie(int movieId){
        Movie movie = movieIRepository.read(movieId);
        List<Reservation> reservationList = new ArrayList<>();

        for(Reservation r: reservationIRepository.read()){
            if(r.getMovieId() == movieId){
                reservationIRepository.delete(r.getId());
                reservationList.add(r);

            }
        }

        undoRedoService.add(new DeleteMovieOperation<Movie>(this.movieIRepository,
                this.reservationIRepository, reservationList, movie));
        movieIRepository.delete(movieId);
    }

    public List<Movie> search(String searchWord){
        int found = 0;

        List<Movie> returnMovieList = new ArrayList<>();

        for(Movie m: this.movieIRepository.read()){
            String title = m.getTitle().toLowerCase();
            if(title.contains(searchWord.toLowerCase())){

                found = 1;
                returnMovieList.add(m);
            }
        }
        if(found == 0){
            System.out.println("Word not found in movie titles");
        }
        return returnMovieList;
    }

    public void statusChange(int movieId,boolean status){

        for(Movie m: this.movieIRepository.read()){
            if(m.getId() == movieId){
                m.setInProgram(status);
            }
        }
    }

    public void changePriceIfLessThenValue(int addValueToPrice,int priceLessThen){
        for(Movie movie: this.movieIRepository.read()){
          if(movie.getPrice() < priceLessThen){
              movie.setPrice(movie.getPrice() + addValueToPrice);
          }
        }
    }
}
