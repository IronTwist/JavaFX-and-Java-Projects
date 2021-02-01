package sample;

import Domain.ReservationNumberPerMovieViewModel;
import Service.ReservationService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ViewMoviesByReservationGraphic {

    public TableView<ReservationNumberPerMovieViewModel> tblMovieByReservation;
    public TableColumn colMovieTitle;
    public TableColumn colNumberOfreservation;
    public TableColumn colNumberOfReservation;
    private ReservationService reservationService;

    private ObservableList<ReservationNumberPerMovieViewModel> observableList = FXCollections.observableArrayList();

    public void setServices(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void initialize() {
        Platform.runLater(()->{
            observableList.clear();
            observableList.addAll(reservationService.getMoviesOrderByNumberReservations());
            tblMovieByReservation.setItems(observableList);
        });
    }
}
