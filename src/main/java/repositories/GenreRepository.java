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
}
