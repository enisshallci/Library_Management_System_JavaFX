package controllers.fxml_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
