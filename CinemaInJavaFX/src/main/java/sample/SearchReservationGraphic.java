package sample;

import Domain.Reservation;
import Service.ReservationService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import sun.rmi.server.LoaderHandler;

import java.util.ArrayList;
import java.util.List;

public class SearchReservationGraphic {
    public TableColumn colIdReservation;
    public TableView reservationTableFind;
    public TableColumn colMovieIdReservation;
    public TableColumn colClientNumberCard;
    public TableColumn colReservationDateTime;
    public TextField txtSearchCard;
    public Button btnSearchReservation;

    private ReservationService reservationService;
    private ObservableList<Reservation> reservationObservableList = FXCollections.observableArrayList();
    List<Reservation> reservationList = new ArrayList<>();

    public void setServices(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void initialize() {
        Platform.runLater(() -> {
            reservationObservableList.addAll(reservationList);
            reservationTableFind.setItems(reservationObservableList);
        });
    }

    public void handleSearchReservation(ActionEvent actionEvent) {
        reservationList.clear();
        String cardNumber = txtSearchCard.getText();

        for(Reservation r: reservationService.search(cardNumber)){
            reservationList.add(r);
        }

        if(reservationList.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Reservation not found");
            alert.show();
            txtSearchCard.clear();
        }

        reservationObservableList.clear();
        reservationObservableList.addAll(reservationList);

    }

}
