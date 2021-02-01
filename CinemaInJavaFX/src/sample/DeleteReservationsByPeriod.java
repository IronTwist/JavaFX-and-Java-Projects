package sample;

import Domain.DateTime;
import Domain.InvalidMovieException;
import Domain.Reservation;
import Service.ReservationService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeleteReservationsByPeriod {


    public TextField txtDayStart;
    public TextField txtMonthStart;
    public TextField txtYearStart;
    public TextField txtDayEnd;
    public TextField txtMonthEnd;
    public TextField txtYearEnd;
    public Button btnDelete;

    private ReservationService reservationService;

    public void setServices(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void initialize() {
    }

    public void deleteFromPeriod(ActionEvent actionEvent) {
        int yearStart = Integer.parseInt(txtYearStart.getText());
        int monthStart = Integer.parseInt(txtMonthStart.getText());
        int dayStart = Integer.parseInt(txtDayStart.getText());
        int yearEnd = Integer.parseInt(txtYearEnd.getText());
        int monthEnd = Integer.parseInt(txtMonthEnd.getText());
        int dayEnd = Integer.parseInt(txtDayEnd.getText());

        DateTime start = new DateTime(yearStart,monthStart,dayStart);
        DateTime end = new DateTime(yearEnd,monthEnd,dayEnd);

        while(!start.getDate().equals(end.getDate())){

            for (Reservation r: this.reservationService.getAll()){
                if(start.getDate().equals(r.getDateTime().getDate())){
                    try {
                        this.reservationService.deleteReservation(r.getId());
                    } catch (InvalidMovieException e) {
                        e.printStackTrace();
                    }
                }
            }

            start.setDate(start.getDate().plusDays(1));
        }

        Stage stage = (Stage) btnDelete.getScene().getWindow();
        stage.close();

    }
}
