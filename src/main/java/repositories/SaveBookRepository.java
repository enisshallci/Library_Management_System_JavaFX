package repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
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

    //3
    public boolean insert(BookModel bookModel, int genreId) {

        if (!bookExists(bookModel.getBookTitle())) {
            String insert = "INSERT INTO BOOK(bookTitle, bookAuthor, bookGenreId, publishedYear, imageSrc, numberOfCopies, available) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            Connection connection = DBConnection.getConnection();
            try {
                PreparedStatement statement = connection.prepareStatement(insert);
                statement.setString(1, bookModel.getBookTitle());
                statement.setString(2, bookModel.getBookAuthor());
                statement.setInt(3, genreId);       // Ketu ki pune.
                statement.setInt(4, bookModel.getPublishedYear());
                statement.setString(5, bookModel.getImgSrc());
                statement.setInt(6, bookModel.getNumberOfCopies());
                statement.setInt(7, bookModel.getAvailable());

                statement.executeUpdate();
                return true;

            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        return false;
    }

    public boolean bookExists(String bookTitle) {
        String query = "SELECT COUNT(*) FROM book WHERE bookTitle = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, bookTitle);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // If count > 0, book exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
