package sample;

import Domain.Movie;
import Service.MovieService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class SearchMovieGraphic {
    public TableView movieTable;
    public TableColumn colIdMovie;
    public TableColumn titleId;
    public TableColumn movieReleaseYearId;
    public TableColumn priceId;
    public TableColumn isInProgramId;
    public TextField txtSearchTitle;
    public TableView movieTableFind;
    public Button btnSearchMovie;

    private MovieService movieService;
    private ObservableList<Movie> movieObservableList = FXCollections.observableArrayList();
    private List<Movie> moviesFind = new ArrayList<>();

    public void setServices(MovieService movieService) {
        this.movieService = movieService;
    }

    public void initialize() {
        Platform.runLater(() -> {
            movieObservableList.addAll(moviesFind);
            movieTableFind.setItems(movieObservableList);
        });
    }


    public void handleSearchMovies(ActionEvent actionEvent) {
        moviesFind.clear();
        String strSearch = txtSearchTitle.getText();

        for (Movie m : this.movieService.search(strSearch)) {
            moviesFind.add(m);
        }

        if(moviesFind.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Movie not found");
            alert.show();
            txtSearchTitle.clear();
        }

        movieObservableList.clear();
        movieObservableList.addAll(moviesFind);
        txtSearchTitle.clear();
    }

}
