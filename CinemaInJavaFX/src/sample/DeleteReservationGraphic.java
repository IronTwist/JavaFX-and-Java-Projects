package sample;

import Service.MovieService;
import Service.ReservationService;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class DeleteReservationGraphic {

    public Button btnDeleteReservation;
    public TextField reservationIdToDelete;
    private ReservationService reservationService;


    public void setServices(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void initialize() {

    }


    public void handleDeleteReservation(ActionEvent actionEvent) {

        try {
            String message = "Are you sure you want to delete the reservation?";
            int id = Integer.parseInt(reservationIdToDelete.getText());
            String data = String.valueOf(reservationService.getAll().get(id).getDateTime().getDate());
            String content = "ID: " + reservationIdToDelete.getText() +
                    "   Card number: " + reservationService.getAll().get(id).getClientNumberCard() +
                    "   Date: " + data;

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(message);
            alert.setContentText(content);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                int reservationId = Integer.parseInt(reservationIdToDelete.getText());
                reservationService.deleteReservation(reservationId);
                Stage stage = (Stage) btnDeleteReservation.getScene().getWindow();
                stage.close();

            }

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("The reservation does not exist!");
            alert.show();
        }
    }
}
