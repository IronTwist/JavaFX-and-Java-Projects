package sample;

import Service.MovieService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeMovieStatusGraphic {
    public TextField txtId;
    public TextField txtStatus;

    public MovieService movieService;
    public Button btnChangeMovieStatus;

    public void setServices(MovieService movieService) {
        this.movieService = movieService;
    }

    public void initialize() {

    }

    public void btnChangeMovieStatus(ActionEvent actionEvent) {
        int id = Integer.parseInt(txtId.getText());
        boolean status = Boolean.parseBoolean(txtStatus.getText());

        movieService.statusChange(id, status);

        Stage stage = (Stage) btnChangeMovieStatus.getScene().getWindow();
        stage.close();
    }
}
