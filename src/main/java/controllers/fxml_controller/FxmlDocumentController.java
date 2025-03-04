package controllers.fxml_controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import models.BookModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FxmlDocumentController implements Initializable {

    @FXML
    private BorderPane mainPane;


    @FXML
    private void buttonAllBooks() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("Screen1AllBooks");
        mainPane.setCenter(view);
    }

    @FXML
    private void buttonSaveBook() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("Screen2CrudBook");
        mainPane.setCenter(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
