package sample;

import Domain.Movie;
import Domain.Reservation;
import Repository.IRepository;
import Repository.InMemoryRepository;
import Service.MovieService;
import Service.ReservationService;
import Service.UndoRedoService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = fxmlLoader.load();

        Controller controller = fxmlLoader.getController();

        IRepository<Movie> movieIRepository = new InMemoryRepository<>();
        IRepository<Reservation> reservationIRepository = new InMemoryRepository<>();

        UndoRedoService undoRedoService = new UndoRedoService(movieIRepository, reservationIRepository);

        ReservationService reservationService = new ReservationService(reservationIRepository, movieIRepository, undoRedoService);
        MovieService movieService = new MovieService(movieIRepository,reservationIRepository,undoRedoService);

        movieService.addMovie("The Matrix",1999,20,true);
        movieService.addMovie("Matrix: Reloaded",2003,12,true);
        movieService.addMovie("Matrix: Revolution",2003,43,true);

        reservationService.addReservation(2,2344,2010,2,3,20,11);
        reservationService.addReservation(1,2345,2011,3,3,14,21);
        reservationService.addReservation(1,2344,2012,1,3,17,04);

        controller.setServices(movieService,reservationService);

        primaryStage.setTitle("Cinema");
        primaryStage.setScene(new Scene(root, 970, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
