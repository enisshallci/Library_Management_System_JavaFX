package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import models.Book;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CardListController implements Initializable {

    @FXML
    private HBox cardLayout;

    private List<Book> recentlyAdded;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        recentlyAdded = new ArrayList<>(recentlyAdded());

        try {
            for (int i = 0; i < recentlyAdded.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(recentlyAdded().get(i));
                cardLayout.getChildren().add(cardBox);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private List<Book> recentlyAdded() {
        List<Book> listBook = new ArrayList<>();

        Book book = new Book();
        book.setName("Harry Potter and \nthe Sorcerer's Stone");
        book.setImageSrc("/images/HarryPotter1.jpg");
        book.setAuthor("J.K Rowling");
        listBook.add(book);

        book = new Book();
        book.setName("Harry Potter and the Sorcerer's Stone");
        book.setImageSrc("/images/HarryPotter1.jpg");
        book.setAuthor("J.K Rowling");
        listBook.add(book);

        book = new Book();
        book.setName("Harry Potter and the Sorcerer's Stone");
        book.setImageSrc("/images/HarryPotter1.jpg");
        book.setAuthor("J.K Rowling");
        listBook.add(book);

        book = new Book();
        book.setName("Harry Potter and the Sorcerer's Stone");
        book.setImageSrc("/images/HarryPotter1.jpg");
        book.setAuthor("J.K Rowling");
        listBook.add(book);

        book = new Book();
        book.setName("Harry Potter and the Sorcerer's Stone");
        book.setImageSrc("/images/HarryPotter1.jpg");
        book.setAuthor("J.K Rowling");
        listBook.add(book);

        return listBook;
    }
}
