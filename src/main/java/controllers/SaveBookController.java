package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.BookModel;
import service.SaveBookService;

import java.net.URL;
import java.util.ResourceBundle;

public class SaveBookController implements Initializable {

    @FXML
    private TableView<BookModel> tableview;

    @FXML
    private TableColumn<BookModel, String> authorColumn;

    @FXML
    private TableColumn<BookModel, Integer> availableCopiesColumn;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TableColumn<BookModel, String> genreColumn;

    @FXML
    private TableColumn<BookModel, Integer> numberOfCopiesColumn;

    @FXML
    private TableColumn<BookModel, Integer> publishedYearColumn;


    @FXML
    private TableColumn<BookModel, String> titleColumn;

    @FXML
    private Button mainButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadTableViewData();
        populateGenreComboBox();
    }

    private final SaveBookService saveBookService;

    public SaveBookController() {

        saveBookService = new SaveBookService();
    }

    private void loadTableViewData() {

        ObservableList<BookModel> bookModelObservableList = saveBookService.loadTableViewData();;

        tableview.setItems(bookModelObservableList);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("bookGenre"));
        publishedYearColumn.setCellValueFactory(new PropertyValueFactory<>("publishedYear"));
        numberOfCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfCopies"));
        availableCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
    }

    private void populateGenreComboBox() {

        comboBox.setItems(saveBookService.getAllGenres());
    }
    @FXML
    private void handleSwitchToMain() {
////        try {
////            // Load the MainView.fxml
////            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
////            Parent mainView = loader.load();
////
////            // Get the current stage
////            Stage stage = (Stage) mainButton.getScene().getWindow();
////
////            // Set the new scene
////            stage.setScene(new Scene(mainView));
////            stage.show();
////
////        } catch (IOException e) {
////            e.printStackTrace();
//        }
        System.out.println("Hamdi");
    }

}
