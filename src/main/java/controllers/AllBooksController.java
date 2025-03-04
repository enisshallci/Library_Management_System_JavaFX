package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.BookModel;
import service.BookService;
import service.CardBookService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AllBooksController implements Initializable {

    @FXML
    private HBox cardLayout;

    private List<BookModel> recentlyAdded;
    private List<BookModel> allBooks;

    @FXML
    private GridPane bookContainer;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        recentlyAdded = new ArrayList<>(recentlyAdded());
        allBooks = new ArrayList<>(allBooks());
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < recentlyAdded.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(recentlyAdded().get(i));
                cardLayout.getChildren().add(cardBox);
            }

            for (BookModel book : allBooks) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("book.fxml"));
                VBox bookBox = fxmlLoader.load();
                BookController bookController = fxmlLoader.getController();
                bookController.setData(book);

                if (column == 5) {
                    column = 0;
                    ++row;
                }

                bookContainer.add(bookBox, column++, row);
                GridPane.setMargin(bookBox, new Insets(10));
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private List<BookModel> recentlyAdded() {
        List<BookModel> listBook = new ArrayList<>();

        CardBookService cardBookService = new CardBookService();
        ObservableList observableList = cardBookService.recentlyAddedBooks();

        for (int i = 0; i < observableList.size(); i++) {
            listBook.add((BookModel) observableList.get(i));
        }

        return listBook;
    }

    private List<BookModel> allBooks() {

        List<BookModel> listBook = new ArrayList<>();

        BookService bookService = new BookService();
        ObservableList observableList = bookService.getAllBooks();

        for (int i = 0; i < observableList.size(); i++) {
            listBook.add((BookModel) observableList.get(i));
        }

        return listBook;
    }

}
