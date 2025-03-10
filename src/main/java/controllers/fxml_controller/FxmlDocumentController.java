package controllers.fxml_controller;

import controllers.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class FxmlDocumentController implements Initializable {

    @FXML
    private BorderPane mainPane;

    @FXML
    private void buttonAllBooks() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("allBooks");
        mainPane.setCenter(view);
    }

    @FXML
    private void buttonSaveBook() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("crudBook");
        mainPane.setCenter(view);
    }

    @FXML
    void buttonAllMembers(ActionEvent event) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("member");
        mainPane.setCenter(view);
    }

    @FXML
    void buttonAddGenre(ActionEvent event) {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/controllers/addGenre.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Add Genre");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
