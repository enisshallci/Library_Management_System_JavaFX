package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.GenreModel;
import models.MemberModel;
import service.GenreService;

import java.net.URL;
import java.util.ResourceBundle;

public class GenreController implements Initializable {

    private final GenreService genreService;

    @FXML
    private TextField bookGenre;

    public GenreController() {
        this.genreService = new GenreService();
    }

    @FXML
    void addGenre(ActionEvent event) {

            String genreName = bookGenre.getText();


            if (genreName.isEmpty()) {
                showAlert1();
                return;
            }

            GenreModel genreModel = new GenreModel(genreName);
            boolean isAdded = genreService.addGenre(genreModel);

            if (isAdded) {
                showAlert2("Genre added successfully.", Alert.AlertType.INFORMATION);
            } else {
                showAlert2("Failed to add genre. Please check your input and try again", Alert.AlertType.WARNING);
            }
    }

    @FXML
    void deleteGenre(ActionEvent event) {

            String genreName = bookGenre.getText();

            if (genreName.isEmpty()) {
                showAlert1();
                return;
            }

            if (genreService.genreExists(genreName)) {

                boolean isDeleted = genreService.deleteGenre(genreName);

                if (isDeleted) {
                    showAlert2("Genre deleted successfully.", Alert.AlertType.INFORMATION);
                }
            }
            else {
                showAlert2("The genre " + genreName + " does not exist", Alert.AlertType.ERROR);
            }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void showAlert1() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Please fill in field before proceeding.");
        alert.showAndWait();
    }

    private void showAlert2(String message, Alert.AlertType type) {

        Alert alert = new Alert(type);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
