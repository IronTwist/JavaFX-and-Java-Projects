package sample;

import Domain.Movie;
import Service.MovieService;
import com.sun.glass.ui.CommonDialogs;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddMovieGraphic {

    public TextField txtMovieTitle;
    public TextField txtReleaseYear;
    public TextField txtMoviePrice;
    public CheckBox chkRunningMovie;
    public Button btnAddMovie;
    public TableColumn colIdMovie;
    public TableColumn titleId;
    public TableColumn movieReleaseYearId;
    public TableColumn priceId;
    public TableColumn isInProgramId;
    public TableView movieTable;
    private MovieService movieService;

    private ObservableList<Movie> movies = FXCollections.observableArrayList();

    public void setServices(MovieService movieService){
        this.movieService = movieService;
    }

    @FXML
    public void initilize(){
        Platform.runLater(() -> {
            movies.addAll(movieService.getAllMovies());
            movieTable.setItems(movies);
        });
    }

    public void buttonAddMovieClick(ActionEvent actionEvent) {

        try {
            String title = txtMovieTitle.getText();
            int year = Integer.parseInt(txtReleaseYear.getText());
            int price = Integer.parseInt(txtMoviePrice.getText());
            boolean isRunning = chkRunningMovie.isSelected();

            movieService.addMovie(title, year, price, isRunning);

            movies.clear();
            movies.addAll(movieService.getAllMovies());
        }catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Faile!");
            alert.setContentText(e.getMessage());
            ButtonType ok = new ButtonType("OK",ButtonBar.ButtonData.YES);
            alert.getButtonTypes().setAll(ok);
            alert.show();
        }
    }
}
