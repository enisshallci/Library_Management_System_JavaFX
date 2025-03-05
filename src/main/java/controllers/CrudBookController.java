package controllers;

import controllers.fxml_controller.FxmlDocumentController;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import models.BookModel;
import models.GenreModel;
import service.GenreService;
import service.BookService;

import java.io.BufferedInputStream;
import java.io.File;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CrudBookController implements Initializable {

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
    private ComboBox<String> genreComboBox;

    @FXML
    private ImageView imageSrc;

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

    @FXML
    private TextField filteredField;

    @FXML
    void deleteBook(ActionEvent event) {
        this.deleteBook();
    }

    @FXML
    void updateBook(ActionEvent event) {

        this.updateBook();
    }

    @FXML
    void uploadImage(ActionEvent event) {
        this.uploadImage();
    }

    @FXML
    void clearDetails(ActionEvent event) {

        clearBookDetails();
    }

    private String imagePath;

    private BookModel selectedBook;
    private final BookService crudBookService;
    private GenreService genreService;

    public CrudBookController() {

        this.crudBookService= new BookService();
        this.genreService = new GenreService();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadTableViewData();
        populateGenreComboBox();
        filteredData();

        tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });
    }


    private void loadTableViewData() {

        ObservableList<BookModel> bookModelObservableList = crudBookService.getAllBooks();

        SortedList<BookModel> sortedList = new SortedList<>(bookModelObservableList);

        sortedList.setComparator(Comparator.comparingInt(BookModel::getId));

        sortedList.comparatorProperty().bind(tableview.comparatorProperty());

        tableview.setItems(sortedList);

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("bookGenre"));
        publishedYearColumn.setCellValueFactory(new PropertyValueFactory<>("publishedYear"));
        numberOfCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfCopies"));
        availableCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
    }

    public void filteredData() {

        ObservableList<BookModel> dataList = crudBookService.getAllBooks();

        FilteredList<BookModel> filteredList = new FilteredList<>(dataList, b -> true);

        filteredField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(bookModel -> {

                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (bookModel.getBookTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else if(bookModel.getBookAuthor().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else
                    return false;
            });
        });

        SortedList<BookModel> sortedList = new SortedList<>(filteredList);

        sortedList.setComparator(Comparator.comparingInt(BookModel::getId));

        sortedList.comparatorProperty().bind(tableview.comparatorProperty());

        tableview.setItems(sortedList);
    }



    private void populateGenreComboBox() {

        genreComboBox.setItems(crudBookService.getAllGenres());
    }


    private void addBook() {

        String title = titleField.getText();
        String author = authorField.getText();
        String genreValue = genreComboBox.getValue();
        String publishedYear = publishedYearField.getText();
        String imageSrc = imagePath;
        String numberOfCopies = numberCopiesField.getText();

        if (title.isEmpty() || author.isEmpty() || genreValue == null || publishedYear.isEmpty() /*|| imageSrc.isEmpty()*/ || numberOfCopies.isEmpty()) {
            showAlert1();
            return;
        }

        GenreModel genreModel = getGenre(genreValue);

        BookModel bookModel = new BookModel(title, author, genreModel.getGenreName(), Integer.parseInt(publishedYear), imageSrc, Integer.parseInt(numberOfCopies));
        boolean isAdded = crudBookService.addBook(bookModel, genreModel.getId());

        if (isAdded) {
            showAlert2("Book added successfully.", Alert.AlertType.INFORMATION);
        } else {
            showAlert2("Book with this title already exists.", Alert.AlertType.WARNING);
        }

        loadTableViewData();
    }


    private GenreModel getGenre(String genreName) {

        return genreService.getGenreByName(genreName);
    }

    private void deleteBook() {

        BookModel selectedBook = tableview.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {

            boolean confirmDelete = showConfirmationDialog("Are you sure you want to delete this book?");
            if (!confirmDelete) {
                return;
            }

            boolean isDeleted = crudBookService.deleteBookById(selectedBook.getId());

            if (isDeleted) {

//                tableview.getItems().remove(selectedBook);
                showAlert2("Book deleted successfully.", Alert.AlertType.INFORMATION);
                loadTableViewData();
            } else {
                showAlert2("Failed to delete the book.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert2("No book selected to delete", Alert.AlertType.WARNING);
        }
    }

    private void populateFields(BookModel book) {

        selectedBook = book;

        titleField.setText(selectedBook.getBookTitle());
        authorField.setText(book.getBookAuthor());
        genreComboBox.setValue(book.getBookGenre());
        publishedYearField.setText(String.valueOf(book.getPublishedYear()));
        numberCopiesField.setText(String.valueOf(book.getNumberOfCopies()));


        if (book.getImageSrc() != null && !book.getImageSrc().isEmpty()) {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(book.getImageSrc())));
            imageSrc.setImage(image);
        }
    }

    private void updateBook() {

//        BookModel selectedBook = tableview.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {

            String genreValue = genreComboBox.getValue();

            boolean confirmUpdate = showConfirmationDialog("Are you sure you want to update this book?");
            if (!confirmUpdate) {
                return;
            }

            selectedBook.setBookTitle(titleField.getText());
            selectedBook.setBookAuthor(authorField.getText());
            selectedBook.setPublishedYear(Integer.parseInt(publishedYearField.getText()));
            selectedBook.setNumberOfCopies(Integer.parseInt(numberCopiesField.getText()));

            GenreModel genreModel = getGenre(genreValue);


            boolean isUpdated = crudBookService.updateBook(selectedBook, genreModel.getId());

            if (isUpdated) {
//                tableview.getItems().remove(selectedBook);
                showAlert2("Book updated successfully.", Alert.AlertType.INFORMATION);
                loadTableViewData();
            } else {
                showAlert2("Failed to update the book.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert2("No book selected to update", Alert.AlertType.WARNING);
        }
    }

    private void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");

        // Set the default directory to open
        File initialDirectory = new File("C:\\Users\\Admin\\Desktop\\Library_Management_DesktopApp\\src\\main\\resources\\images\\covers");
        if (initialDirectory.exists()) {
            fileChooser.setInitialDirectory(initialDirectory);
        }

        // Allow only image files
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );

        // Show file chooser
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            imagePath = "/images/covers/" + file.getName();
            Image image = new Image(file.toURI().toString());
            imageSrc.setImage(image);
        }
    }


    private boolean showConfirmationDialog(String message) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);

        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }


    private void showAlert1() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("All fields are required. Please fill in every field before proceeding.");
        alert.showAndWait();
    }


    private void showAlert2(String message, Alert.AlertType type) {

        Alert alert = new Alert(type);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearBookDetails() {

        titleField.clear();
        authorField.clear();
        numberCopiesField.clear();
        publishedYearField.clear();
        genreComboBox.setValue("Enter Book Genre");
        imageSrc.setImage(null);
    }
}