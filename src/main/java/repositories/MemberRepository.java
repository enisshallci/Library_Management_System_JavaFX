package repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.MemberModel;
import service.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRepository {

    //1
    public ObservableList<MemberModel> getAllMembers() {

        String query = "SELECT id, firstName, lastName, gender, email, phoneNumber FROM members;";

        ObservableList<MemberModel> memberList = FXCollections.observableArrayList();

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement stm = connection.prepareStatement(query);
            ResultSet results = stm.executeQuery();

            while (results.next()) {
                int id = results.getInt("id");
                String firstName = results.getString("firstName");
                String lastName = results.getString("lastName");
                char gender = results.getString("gender").charAt(0);
                String email = results.getString("email");
                String phoneNumber = results.getString("phoneNumber");

                MemberModel member = new MemberModel(id, firstName, lastName, gender, email, phoneNumber);
                memberList.add(member);
            }

            results.close();
            stm.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return memberList;
    }

    //2
    public boolean insert(MemberModel memberModel) {

        String insert = "INSERT INTO members (firstName, lastName, gender, email, phoneNumber) " +
                "VALUES (?, ?, ?, ?, ?);";

        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setString(1, memberModel.getFirstName());
            statement.setString(2, memberModel.getLastName());
            statement.setString(3, String.valueOf(memberModel.getGender()));
            statement.setString(4, memberModel.getEmail());
            statement.setString(5, memberModel.getPhoneNumber());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(e);
        }

        return false;
    }

    public static void main(String[] args) {

        MemberRepository memberRepository = new MemberRepository();
        System.out.println(memberRepository.getAllMembers());

        memberRepository.insert(new MemberModel("Enis", "SHallci", 'M', "enis.shallci@gmail.com", "04124-11242-12354"));
    }

    //3
    public boolean deleteMember(int bookId) {
        String deleteQuery = "DELETE FROM members WHERE id = ?";

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

    //4
    public boolean updateMember(MemberModel member) {
        String update = "UPDATE members SET firstName = ?, lastName = ?, gender = ?, email = ?, phoneNumber = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(update)) {

            preparedStatement.setString(1, member.getFirstName());
            preparedStatement.setString(2, member.getLastName());
            preparedStatement.setString(3, String.valueOf(member.getGender()));
            preparedStatement.setString(4, member.getEmail());
            preparedStatement.setString(5, member.getPhoneNumber());
            preparedStatement.setInt(6, member.getId());

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

    //5
    public int countMale() {
        String query = "SELECT COUNT(*) AS maleCount FROM members WHERE GENDER = 'M';";
        int maleCount = 0;
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                maleCount = resultSet.getInt("maleCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maleCount;
    }

    //6
    public int countFemale() {
        String query = "SELECT COUNT(*) AS maleCount FROM members WHERE GENDER = 'F';";
        int femaleCount = 0;

        Connection connection = DBConnection.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                femaleCount = resultSet.getInt("maleCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return femaleCount;
    }


//    //3
//    public boolean memberExists(String bookTitle) {
//        String query = "SELECT COUNT(*) FROM book WHERE bookTitle = ?";
//        try (Connection connection = DBConnection.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//
//            statement.setString(1, bookTitle);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    return resultSet.getInt(1) > 0; // If count > 0, book exists
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
