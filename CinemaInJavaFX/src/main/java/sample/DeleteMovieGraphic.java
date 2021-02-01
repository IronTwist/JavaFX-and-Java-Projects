package sample;

import Domain.Movie;
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
            String title = "";

            for(Movie m: movieService.getAllMovies()){
                if(m.getId() == id){
                    title = m.getTitle();
                }
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(message);
            alert.setContentText("The folowing movie will be deleted: " + title);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                int movieId = Integer.parseInt(movieIdToDelete.getText());
                movieService.deleteMovie(movieId);
                Stage stage = (Stage) btnDeleteMovie.getScene().getWindow();
                stage.close();
            }

        }catch (Exception e){
            e.printStackTrace();
            Alert eAlert = new Alert(Alert.AlertType.ERROR);
            eAlert.setTitle("Error");
            eAlert.setContentText(e.toString());
            eAlert.show();
        }
    }
}
