package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import models.BookModel;

import java.util.Objects;

public class CardController {

    @FXML
    private Label authorName;

    @FXML
    private Label bookName;

    @FXML
    private ImageView bookImage;

    @FXML
    private HBox box;

    private String [] colors = {"B9E5FF", "BDB2FE", "FB9AA8", "FF5056"};

    private static int colorNumber = 1;

    public void setData(BookModel book) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(book.getImageSrc())));
        bookImage.setImage(image);
        bookName.setText(book.getBookTitle());
        authorName.setText(book.getBookAuthor());

        box.setStyle("-fx-background-color: #" + colors[(colorNumber % 4)] + ";" +
                    "-fx-background-radius: 15;" +
                    "-fx-effect: dropShadown(three-pass-box, rgba(0, 0, 0, 0), 10, 0, 0, 10);");
        colorNumber++;
        }
    }

