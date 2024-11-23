package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.BookModel;
import repositories.SaveBookRepository;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SaveBookController implements Initializable {

    @FXML
    private TableView<BookModel> tableview;

    @FXML
    private TableColumn<BookModel, String> authorColumn;

    @FXML
    private TableColumn<BookModel, Integer> availableCopiesColumn;

    @FXML
    private ComboBox comboBox;

    @FXML
    private TableColumn<BookModel, String> genreColumn;

    @FXML
    private TableColumn<BookModel, Integer> numberOfCopiesColumn;

    @FXML
    private TableColumn<BookModel, Integer> publishedYearColumn;


    @FXML
    private TableColumn<BookModel, String> titleColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadTableViewData();

        //____________________________
    }

    private SaveBookRepository saveBookRepository;

    public SaveBookController() {       // Konstruktori

        saveBookRepository = new SaveBookRepository();
    }

    public void loadTableViewData() {

        List<BookModel> listOfBooks = saveBookRepository.loadTableViewData();

        ObservableList<BookModel> bookModelObservableList = FXCollections.observableArrayList(listOfBooks);

        tableview.setItems(bookModelObservableList);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("bookGenre"));
        publishedYearColumn.setCellValueFactory(new PropertyValueFactory<>("publishedYear"));
    }

}
