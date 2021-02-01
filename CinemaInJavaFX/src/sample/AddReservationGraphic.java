package sample;

import Domain.DateTime;
import Domain.Reservation;
import Service.MovieService;
import Service.ReservationService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class AddReservationGraphic {


    public TextField txtMovieId;
    public TextField txtClientNumberCard;
    public Button btnAddReservation;
    public TableView reservationTable;
    public TableColumn colIdReservation;
    public TableColumn colMovieIdReservation;
    public TableColumn colClientNumberCard;
    public TableColumn colReservationDateTime;


    private MovieService movieService;
    private ReservationService reservationService;

    private ObservableList<Reservation> reservationObservableList = FXCollections.observableArrayList();

    public void setServices(MovieService movieService, ReservationService reservationService) {
        this.movieService = movieService;
        this.reservationService = reservationService;
    }

    public void initialize() {
        Platform.runLater(()->{
            reservationObservableList.clear();
            reservationObservableList.addAll(reservationService.getAll());
            reservationTable.setItems(reservationObservableList);
        });
    }

    public void buttonAddReservationClick(ActionEvent actionEvent) {
        try {
            int movieId = Integer.parseInt(txtMovieId.getText());

            int card = Integer.parseInt(txtClientNumberCard.getText());

            reservationService.addReservation(movieId, card, new DateTime());

            reservationObservableList.clear();
            reservationObservableList.addAll(reservationService.getAll());
        }catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setContentText(e.getMessage());
            ButtonType ok = new ButtonType("OK",ButtonBar.ButtonData.YES);
            alert.getButtonTypes().setAll(ok);
            alert.show();
        }
    }

}
