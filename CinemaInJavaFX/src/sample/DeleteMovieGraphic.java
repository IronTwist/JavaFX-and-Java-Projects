package sample;

import Service.MovieService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class DeleteMovieGraphic {
    public TextField movieIdToDelete;
    public Button btnDeleteMovie;
    private MovieService movieService;


    public void setServices(MovieService movieService) {
        this.movieService = movieService;
    }

    public void initialize() {

    }

    public void handleDeleteMovie(ActionEvent actionEvent) throws IOException {
        try {
            String message = "Are you sure you want to delete the movie?";
            int id = Integer.parseInt(movieIdToDelete.getText());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(message);
            alert.setContentText("ID: " + movieIdToDelete.getText() + "   Title: " + movieService.getAllMovies().get(id).getTitle());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                int movieId = Integer.parseInt(movieIdToDelete.getText());
                movieService.deleteMovie(movieId);
                Stage stage = (Stage) btnDeleteMovie.getScene().getWindow();
                stage.close();
            }

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("The movie does not exist!");
            alert.show();
        }
    }
}
