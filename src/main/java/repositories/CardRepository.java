package repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.BookModel;
import service.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardRepository {

    public ObservableList<BookModel> getRecentlyAddedBooks() {

        ObservableList<BookModel> recentBooks = FXCollections.observableArrayList();

        String query = "SELECT book.id, book.bookTitle, book.bookAuthor, genres.genreName AS bookGenre, book.publishedYear," +
                "book.imageSrc, book.numberOfCopies FROM book JOIN genres ON book.bookGenreId = genres.id " +
                "ORDER BY book.id DESC LIMIT 5;";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BookModel book = new BookModel(
                        rs.getInt("id"),
                        rs.getString("bookTitle"),
                        rs.getString("bookAuthor"),
                        rs.getString("bookGenre"),
                        rs.getInt("publishedYear"),
                        rs.getString("imageSrc"),
                        rs.getInt("numberOfCopies")
                );

                recentBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recentBooks;
    }
}

