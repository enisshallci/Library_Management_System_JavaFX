package repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.BookModel;
import service.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaveBookRepository {

    //1
    public ObservableList<BookModel> loadTableViewData() {
        String query = "SELECT book.id, book.bookTitle, book.bookAuthor, genres.genreName AS bookGenre, book.publishedYear," +
                "book.imageSrc, book.numberOfCopies FROM book JOIN genres ON book.bookGenreId = genres.id;";

        ObservableList<BookModel> bookList = FXCollections.observableArrayList();

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet results = stm.executeQuery();

            while (results.next()) {
                int id = results.getInt("id");
                String bookTitle = results.getString("bookTitle");
                String bookAuthor = results.getString("bookAuthor");
                String bookGenre = results.getString("bookGenre");
                int publishedYear = results.getInt("publishedYear");
                String imageSrc = results.getString("imageSrc");
                int numberOfCopies = results.getInt("numberOfCopies");

                BookModel book = new BookModel(id, bookTitle, bookAuthor, bookGenre, publishedYear, imageSrc, numberOfCopies);
                bookList.add(book);
            }

            results.close();
            stm.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return bookList;
    }

    //2
    public ObservableList<String> getAllGenres() {

        ObservableList<String> genres = FXCollections.observableArrayList();
        String query = "SELECT genreName FROM genres";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                genres.add(resultSet.getString("genreName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genres;
    }

}
