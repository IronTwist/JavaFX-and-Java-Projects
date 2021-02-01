package Service;

import Domain.*;
import Repository.IRepository;

import java.security.KeyException;
import java.util.*;

public class ReservationService {

    private IRepository<Reservation> reservationIRepository;
    private IRepository<Movie> movieIRepository;
    private UndoRedoService undoRedoService;
    private ReservationValidator reservationValidator;

    public ReservationService(IRepository<Reservation> reservationIRepository,IRepository<Movie> movieIRepository, UndoRedoService undoRedoService) {
        this.reservationIRepository = reservationIRepository;
        this.movieIRepository = movieIRepository;
        this.undoRedoService = undoRedoService;
        reservationValidator = new ReservationValidator();
    }

    public void addReservation(int idMovie, int clientCard, DateTime dateTime){
        if(this.movieIRepository.read(idMovie) == null){
            throw new InvalidReservationException("The movie does not exist!");
        }

        if(!this.movieIRepository.read(idMovie).isInProgram()){
            throw new InvalidReservationException("The movie does not run in cinema now!");
        }

        Reservation reservation = new Reservation(idMovie, clientCard, dateTime);
        this.reservationValidator.validate(reservation);
        this.reservationIRepository.create(reservation);
        undoRedoService.add(new AddOperation<Reservation>(this.reservationIRepository, reservation));
    }

    public boolean undo(){
        return undoRedoService.undo();
    }
    public boolean redo(){
        return undoRedoService.redo();
    }

    public void addReservation(int idMovie, int clientCard, int year, int month, int day, int hour, int minute){

        if(this.movieIRepository.read(idMovie) == null){
            throw new InvalidReservationException("The movie does not exist!");
        }

        Reservation reservation = new Reservation(idMovie, clientCard, new DateTime(year,month,day,hour,minute));
        this.reservationValidator.validate(reservation);
        this.reservationIRepository.create(reservation);
        undoRedoService.add(new AddOperation<Reservation>(this.reservationIRepository, reservation));
    }

    public List<Reservation> getAll(){
            return this.reservationIRepository.read();
    }

    public void deleteReservation(int idReservation){
       Reservation reservation = this.reservationIRepository.read(idReservation);
       undoRedoService.add(new DeleteOperation<Reservation>(this.reservationIRepository, reservation));
       this.reservationIRepository.delete(idReservation);
    }

    public List<Reservation> search(String searchWord){
     //   int wordToInt = Integer.parseInt(searchWord);
        int found = 0;

        List<Reservation> returnReservationList = new ArrayList<>();

        for(Reservation r: this.reservationIRepository.read()){
            if(String.valueOf(r.getClientNumberCard()).contains(searchWord)){
               // System.out.println(r.toString());
                found = 1;
                returnReservationList.add(r);
            }
        }
        if(found == 0){
            System.out.println("Client card not found!");
        }

        return returnReservationList;
    }

    public List<Reservation> listAtGivenTime(int startHour,int endHour){
        List<Reservation> returnReservationList = new ArrayList<>();

        for(Reservation r: this.reservationIRepository.read()){

            int hour = r.getDateTime().getTime().getHour();

            if(hour >= startHour && hour < endHour){
                returnReservationList.add(r);
            }
        }
        return returnReservationList;
    }

    public List<ReservationNumberPerMovieViewModel> getMoviesOrderByNumberReservations(){
        List<ReservationNumberPerMovieViewModel> returnList = new ArrayList<>();

        for(Movie movie: this.movieIRepository.read()){
            String titleMovie = movie.getTitle();
            int numberReservation = countReservations(movie.getId());
            returnList.add(new ReservationNumberPerMovieViewModel(titleMovie,numberReservation));
        }

        sort(returnList);

        return returnList;
    }

    private void sort(List<ReservationNumberPerMovieViewModel> returnList) {
        returnList.sort(
               (a, b) -> {
                   if(a.getNumberOfReservation() > b.getNumberOfReservation()){
                       return -1;
                   }else if(a.getNumberOfReservation() < b.getNumberOfReservation()){
                       return 1;
                   }else {
                       return 0;
                   }
               }
       );
    }

    private void sortCard(List<ReservationNumberPerCardModelView> returnList) {
        returnList.sort(
                (a, b) -> Integer.compare(b.getNumberOfReservations(), a.getNumberOfReservations())
        );
    }

    public int countReservations(int movieId){
        int count = 0;
        for(Reservation r: this.reservationIRepository.read()){
            if(r.getMovieId() == movieId){
                count++;
            }
        }
        return count;
    }

    public Set<Integer> getAllCards(){
        Set<Integer> returnCardList = new HashSet<>();

        for(Reservation r: this.reservationIRepository.read()){
            returnCardList.add(r.getClientNumberCard());
        }

        return returnCardList;
    }

    public List<ReservationNumberPerCardModelView> getReservationNumberPerCard(){
        List<ReservationNumberPerCardModelView> returnList = new ArrayList<>();
        Set<Integer> allCards = getAllCards();

        for (Integer card : allCards) {
            int numberOfReservations = countCardPerReservations(card);

            returnList.add(new ReservationNumberPerCardModelView(card, numberOfReservations));
        }

        sortCard(returnList);

        return returnList;
    }

    public int countCardPerReservations(int cardNumber){
        int count = 0;
        for(Reservation r: this.reservationIRepository.read()){
            if(cardNumber == r.getClientNumberCard()){
                count++;
            }
        }
        return count;
    }
}
