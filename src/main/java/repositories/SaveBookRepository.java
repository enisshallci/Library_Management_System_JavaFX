package repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.BookModel;
import service.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SaveBookRepository {

    private Connection connection;

    public List<BookModel> loadTableViewData() {
        // SQL query to fetch book data with genre names
        String query = "SELECT book.id, book.bookTitle, book.bookAuthor, genres.genreName AS bookGenre, book.publishedYear," +
                "book.imageSrc FROM book JOIN genres ON book.bookGenreId = genres.id;";

        // Initialize the connection
        connection = DBConnection.getConnection();

        // ObservableList to hold the books
        ObservableList<BookModel> bookList = FXCollections.observableArrayList();

        try {
            // Prepare and execute the query
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet results = stm.executeQuery();

            // Iterate through the ResultSet and extract book data
            while (results.next()) {
                // Extract data for each book
                int id = results.getInt("id");
                String bookTitle = results.getString("bookTitle");
                String bookAuthor = results.getString("bookAuthor");
                String bookGenre = results.getString("bookGenre");
                int publishedYear = results.getInt("publishedYear");
                String imageSrc = results.getString("imageSrc");

                // Create a Book object and add it to the list
                BookModel book = new BookModel(id, bookTitle, bookAuthor, bookGenre, publishedYear, imageSrc);
                bookList.add(book);
//                System.out.println(book.getBookTitle());
            }

            // Close the statement and result set
            results.close();
            stm.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return bookList;
        /*
         The bookList needs to be populated with data inside the try block,
         but it should still be returned from the method after the try-catch block finishes executing.
         This way, if an exception occurs during the database query, the method will handle it gracefully, and you'll
         still return an empty list or handle the error appropriately.
        */
    }

//______________________________________________________________________________________________________________________


}
