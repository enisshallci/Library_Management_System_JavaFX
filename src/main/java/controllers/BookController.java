package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.BookModel;

import java.util.Objects;

public class BookController {

    @FXML
    private Label bookAuthor;

    @FXML
    private ImageView bookImage;

    @FXML
    private Label bookName;

    public void setData(BookModel bookModel) {

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(bookModel.getImageSrc())));
        bookImage.setImage(image);
        bookName.setText(bookModel.getBookTitle());
        bookAuthor.setText(bookModel.getBookAuthor());
    }

}
