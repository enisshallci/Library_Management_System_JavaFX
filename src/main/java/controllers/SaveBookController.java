package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SaveBookController implements Initializable {

    @FXML
    private ComboBox comb;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> lista = FXCollections.observableArrayList("Science Fiction", "Fantasy","History","Philosophy", "Science", "Travel", "Biography");
        comb.setItems(lista);
        comb.setValue(lista.get(0));
    }

}
