package sample;

import Domain.Movie;
import Domain.Reservation;
import Service.MovieService;
import Service.ReservationService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {


    public TableView tblMovies;
    public TableColumn colIdMovie;
    public TableView tblReservation;
    public TableColumn colIdReservation;
    public TableColumn movieId;
    public TableColumn clientNumberCardId;
    public TableColumn DateTimeId;
    public TableColumn titleId;
    public TableColumn movieReleaseYearId;
    public TableColumn priceId;
    public TableColumn isInProgramId;
    public MenuItem btnExit;


    private MovieService movieService;
    private ReservationService reservationService;

    private ObservableList<Movie> moviesObservable = FXCollections.observableArrayList();
    private ObservableList<Reservation> reservationObservableList = FXCollections.observableArrayList();

    public void setServices(MovieService movieService, ReservationService reservationService) {
        this.movieService = movieService;
        this.reservationService = reservationService;
    }


    @FXML
    private void initialize(){
        Platform.runLater(() -> {
            moviesObservable.addAll(movieService.getAllMovies());
            tblMovies.setItems(moviesObservable);

            reservationObservableList.addAll(reservationService.getAll());
            tblReservation.setItems(reservationObservableList);
        });

    }

    public void addMovie(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddMovieGraphic.fxml"));
        Parent root = fxmlLoader.load();

        AddMovieGraphic addMovieGraphic = fxmlLoader.getController();
        addMovieGraphic.setServices(movieService);
        addMovieGraphic.initilize();

        Stage newStage = new Stage();
        newStage.setTitle("Add Movie");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    public void refreshData(ActionEvent actionEvent) {
        moviesObservable.clear();
        moviesObservable.addAll(movieService.getAllMovies());
        reservationObservableList.addAll(reservationService.getAll());
        moviesObservable.clear();
        reservationObservableList.clear();

        moviesObservable.clear();
        moviesObservable.addAll(movieService.getAllMovies());
        reservationObservableList.addAll(reservationService.getAll());

    }

    public void autoUpdate(){
        moviesObservable.clear();
        moviesObservable.addAll(movieService.getAllMovies());
        reservationObservableList.addAll(reservationService.getAll());
        moviesObservable.clear();
        reservationObservableList.clear();

        moviesObservable.clear();
        moviesObservable.addAll(movieService.getAllMovies());
        reservationObservableList.addAll(reservationService.getAll());
    }

    public void deleteMovie(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeleteMovieGraphic.fxml"));
        Parent root = fxmlLoader.load();

        DeleteMovieGraphic deleteMovieGraphic = fxmlLoader.getController();
        deleteMovieGraphic.setServices(movieService);
        deleteMovieGraphic.initialize();

        Stage deleteStage = new Stage();
        deleteStage.setTitle("Delete Movie");
        deleteStage.setScene(new Scene(root));
        deleteStage.show();
    }

    public void exit(ActionEvent actionEvent) throws IOException {
        Platform.exit();
    }

    public void addReservation(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddReservationGraphic.fxml"));
        Parent root = fxmlLoader.load();

        AddReservationGraphic addReservationGraphic = fxmlLoader.getController();
        addReservationGraphic.setServices(movieService, reservationService);
        addReservationGraphic.initialize();

        Stage addReservationStage = new Stage();
        addReservationStage.setTitle("Add Reservation");
        addReservationStage.setScene(new Scene(root));
        addReservationStage.show();
    }

    public void deleteMovieReservation(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeleteReservationGraphic.fxml"));
        Parent root = fxmlLoader.load();

        DeleteReservationGraphic deleteReservationGraphic = fxmlLoader.getController();
        deleteReservationGraphic.setServices(reservationService);
        deleteReservationGraphic.initialize();

        Stage deleteReservationStage = new Stage();
        deleteReservationStage.setTitle("Delete Reservation");
        deleteReservationStage.setScene(new Scene(root));
        deleteReservationStage.show();
    }

    public void handleUndo(ActionEvent actionEvent) {
        movieService.undo();

        moviesObservable.clear();
        moviesObservable.addAll(movieService.getAllMovies());
        tblMovies.setItems(moviesObservable);
        reservationObservableList.clear();
        reservationObservableList.addAll(reservationService.getAll());
        tblReservation.setItems(reservationObservableList);
    }

    public void handleRedo(ActionEvent actionEvent) {
        movieService.redo();

        moviesObservable.clear();
        moviesObservable.addAll(movieService.getAllMovies());
        tblMovies.setItems(moviesObservable);
        reservationObservableList.clear();
        reservationObservableList.addAll(reservationService.getAll());
        tblReservation.setItems(reservationObservableList);
    }

    public void handleSearchMovies(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SearchMovieGraphic.fxml"));
        Parent root = fxmlLoader.load();

        SearchMovieGraphic searchMovieGraphic = fxmlLoader.getController();
        searchMovieGraphic.setServices(movieService);
        searchMovieGraphic.initialize();

        Stage searchMoviesStage = new Stage();
        searchMoviesStage.setTitle("Search for Movie");
        searchMoviesStage.setScene(new Scene(root));
        searchMoviesStage.show();

    }

    public void handleSearchReservation(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SearchReservationGraphic.fxml"));
        Parent root = fxmlLoader.load();

        SearchReservationGraphic searchReservationGraphic = fxmlLoader.getController();
        searchReservationGraphic.setServices(reservationService);
        searchReservationGraphic.initialize();

        Stage searchReservationStage = new Stage();
        searchReservationStage.setTitle("Search for Reservation");
        searchReservationStage.setScene(new Scene(root));
        searchReservationStage.show();


    }

    public void handleViewCardsByReservations(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewCardsByReservationsGraphic.fxml"));
        Parent root = fxmlLoader.load();

        ViewCardsByReservationsGraphic viewCardsByReservationsGraphic = fxmlLoader.getController();
        viewCardsByReservationsGraphic.setServices(reservationService);
        viewCardsByReservationsGraphic.initialize();

        Stage viewCardsByReservationsGraphicStage = new Stage();
        viewCardsByReservationsGraphicStage.setTitle("Cards by number of reservations");
        viewCardsByReservationsGraphicStage.setScene(new Scene(root));
        viewCardsByReservationsGraphicStage.show();
    }

    public void handleShowMoviesOrderByNrReservations(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewMoviesByReservationGraphic.fxml"));
        Parent root = fxmlLoader.load();

        ViewMoviesByReservationGraphic viewMoviesByReservationGraphic = fxmlLoader.getController();
        viewMoviesByReservationGraphic.setServices(reservationService);
        viewMoviesByReservationGraphic.initialize();

        Stage viewMoviesByReservationStage = new Stage();
        viewMoviesByReservationStage.setTitle("Movies by reservations");
        viewMoviesByReservationStage.setScene(new Scene(root));
        viewMoviesByReservationStage.show();
    }

    public void handleMovieStatusChange(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChangeMovieStatusGraphic.fxml"));
        Parent root = fxmlLoader.load();

        ChangeMovieStatusGraphic changeMovieStatusGraphic = fxmlLoader.getController();
        changeMovieStatusGraphic.setServices(movieService);
        changeMovieStatusGraphic.initialize();

        Stage changeMoviesStatusStage = new Stage();
        changeMoviesStatusStage.setTitle("Change movie status");
        changeMoviesStatusStage.setScene(new Scene(root));
        changeMoviesStatusStage.show();

    }

    public void handleDeleteAllReservationsInAGivenInterval(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeleteReservationsByPeriodGraphic.fxml"));
        Parent root = fxmlLoader.load();

        DeleteReservationsByPeriod delResByPer = fxmlLoader.getController();
        delResByPer.setServices(reservationService);
        delResByPer.initialize();

        Stage delResByPerStage = new Stage();
        delResByPerStage.setTitle("Delete reservations by period of days");
        delResByPerStage.setScene(new Scene(root));
        delResByPerStage.show();
    }

    public void authorDetails(ActionEvent actionEvent) {
        String message = "Author name: Fratean Radu Razvan" +
                         "   Version: 1.0";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About the author");
        alert.setHeaderText("Developer details");
        alert.setContentText(message);
        alert.show();
    }
}
