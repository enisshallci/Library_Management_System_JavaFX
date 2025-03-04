package repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.BookModel;
import service.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRepository {


    //1
    public ObservableList<BookModel> getAllBooks() {
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
                statement.setInt(3, genreId);
                statement.setInt(4, bookModel.getPublishedYear());
                statement.setString(5, bookModel.getImageSrc());
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

    //3
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


    //4
    public boolean deleteBookById(int bookId) {
        String deleteQuery = "DELETE FROM book WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setInt(1, bookId);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    //5
    public boolean updateBook(BookModel bookModel, int genreId) {
        String update = "UPDATE BOOK SET bookTitle = ?, bookAuthor = ?, bookGenreId = ?, publishedYear = ?, numberOfCopies = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(update)) {

            preparedStatement.setString(1, bookModel.getBookTitle());
            preparedStatement.setString(2, bookModel.getBookAuthor());
            preparedStatement.setInt(3, genreId);
            preparedStatement.setInt(4, bookModel.getPublishedYear());
            preparedStatement.setInt(5, bookModel.getNumberOfCopies());
            preparedStatement.setInt(6, bookModel.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            }

            return false;
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }


}

// TODO: Se pari rregulloje kodin ne ate forme qe kur te e klikon nje rresht ne table view, te mbushen fushat anash.