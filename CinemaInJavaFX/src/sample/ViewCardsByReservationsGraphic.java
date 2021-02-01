package sample;

import Domain.ReservationNumberPerCardModelView;
import Service.ReservationService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class ViewCardsByReservationsGraphic {

    public TableView txtReservationNumberTable;
    public TableColumn colCardNumber;
    public TableColumn colNumberOfReservation;
    private ReservationService reservationService;

    private ObservableList<ReservationNumberPerCardModelView> observableList = FXCollections.observableArrayList();

    public void setServices(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void initialize() {
        Platform.runLater(()->{
            observableList.clear();
            observableList.addAll(reservationService.getReservationNumberPerCard());
            txtReservationNumberTable.setItems(observableList);
        });
    }
}
