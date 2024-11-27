package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.BookModel;
import models.GenreModel;
import service.GenreService;
import service.SaveBookService;

import java.net.URL;
import java.util.ResourceBundle;

public class SaveBookController implements Initializable {

    @FXML
    private TextField authorField;

    @FXML
    private TextField numberCopiesField;

    @FXML
    private TextField publishedYearField;

    @FXML
    private TextField titleField;

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

    @FXML
    void addBook(ActionEvent event) {

        this.addBook();
    }

    private final SaveBookService saveBookService;
    private GenreService genreService;

    public SaveBookController() {

        this.saveBookService = new SaveBookService();
        this.genreService = new GenreService();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadTableViewData();
        populateGenreComboBox();
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

    private void addBook() {

        String title = titleField.getText();
        String author = authorField.getText();
        String genreValue = comboBox.getValue();
        String publishedYear =publishedYearField.getText();
        String imageSrc = ".../s.ds";
        String numberOfCopies = numberCopiesField.getText();

        if (title.isEmpty() || author.isEmpty() || genreValue.isEmpty() || publishedYear.isEmpty() /*|| imageSrc.isEmpty()*/ || numberOfCopies.isEmpty() || genreValue.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("All fields are required. Please fill in every field before proceeding.");
            alert.showAndWait();
            return;
        }

        GenreModel genreModel = getGenre(genreValue);

        BookModel bookModel = new BookModel(title, author, genreModel.getGenreName(),  Integer.parseInt(publishedYear), imageSrc, Integer.parseInt(numberOfCopies));
        boolean isAdded = saveBookService.addBook(bookModel);

        if (isAdded) {
            showAlert("Book added successfully.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Book with this title already exists.", Alert.AlertType.INFORMATION);
        }

        loadTableViewData();
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private GenreModel getGenre(String genreName) {

        return genreService.getGenreByName(genreName);
    }

}
