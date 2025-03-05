package repositories;

import models.GenreModel;
import service.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRepository {

    //1
    public GenreModel getGenreByName(String genreName) {

        String query = "SELECT id, genreName FROM genres WHERE genreName = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, genreName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("genreName");
                    return new GenreModel(id, name);
                } else {
                    throw new IllegalArgumentException("Genre not found: " + genreName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error while fetching genre.", e);
        }
    }

    //2
    public boolean insert(GenreModel genreModel) {

        String insert = "INSERT INTO genres (genreName) " +
                "VALUES (?);";

        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setString(1, genreModel.getGenreName());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(e);
        }

        return false;
    }

    //3
    public boolean deleteGenre(String genreName) {

        String deleteQuery = "DELETE FROM genres WHERE genreName = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setString(1, genreName);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    //4
    public boolean genreExists(String bookTitle) {
        String query = "SELECT COUNT(*) FROM genres WHERE genreName = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, bookTitle);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
