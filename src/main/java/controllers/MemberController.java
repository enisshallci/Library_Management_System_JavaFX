package controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.GenreModel;
import models.MemberModel;
import service.MemberService;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class MemberController implements Initializable {

    private MemberModel selectedMember;

    @FXML
    private TableColumn<?, ?> emailColumn;

    @FXML
    private TextField emailField;

    @FXML
    private TextField filteredField;

    @FXML
    private TableColumn<?, ?> firstNameColumn;

    @FXML
    private TextField firstNameField;

    @FXML
    private TableColumn<?, ?> genderColumn;

    @FXML
    private TableColumn<?, ?> lastNameColumn;

    @FXML
    private TextField lastNameField;

    @FXML
    private TableColumn<?, ?> phoneColumn;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private ComboBox<String> genderField;

    @FXML
    private TableView<MemberModel> tableview;

    @FXML
    void addMember(ActionEvent event) {
        this.addMember();
    }

    @FXML
    void clearDetails(ActionEvent event) {
        this.clearMemberDetails();
    }

    @FXML
    void deleteMember(ActionEvent event) {
        this.deleteMember();
    }

    @FXML
    void updateMember(ActionEvent event) {
        this.updateMember();
    }

    @FXML
    private PieChart pieChart;


    private final MemberService memberService;

    public MemberController() {
        this.memberService = new MemberService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadTableViewData();
        this.filteredData();
        genderField.setItems(FXCollections.observableArrayList("M", "F"));
        setPieChart();

        tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });
    }

    private void loadTableViewData() {

        ObservableList<MemberModel> memberModelObservableList = memberService.getAllMembers();

        SortedList<MemberModel> sortedList = new SortedList<>(memberModelObservableList);

        sortedList.setComparator(Comparator.comparingInt(MemberModel::getId));

        sortedList.comparatorProperty().bind(tableview.comparatorProperty());

        tableview.setItems(sortedList);

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }

    public void filteredData() {

        ObservableList<MemberModel> dataList = memberService.getAllMembers();

        FilteredList<MemberModel> filteredList = new FilteredList<>(dataList, b -> true);

        filteredField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(memberModel -> {

                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (memberModel.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else if(memberModel.getLastName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else
                    return false;
            });
        });

        SortedList<MemberModel> sortedList = new SortedList<>(filteredList);

        sortedList.setComparator(Comparator.comparingInt(MemberModel::getId));

        sortedList.comparatorProperty().bind(tableview.comparatorProperty());

        tableview.setItems(sortedList);
    }

    private void populateFields(MemberModel member) {

        selectedMember = member;

        firstNameField.setText(member.getFirstName());
        lastNameField.setText(member.getLastName());
        genderField.setValue(String.valueOf(member.getGender()));
        emailField.setText(member.getEmail());
        phoneNumberField.setText(member.getPhoneNumber());
    }

    private void addMember() {

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String gender = genderField.getValue();
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || gender == null || email.isEmpty() || phoneNumber.isEmpty()) {
            showAlert1();
            return;
        }

        MemberModel memberModel = new MemberModel(firstName, lastName, gender.charAt(0), email, phoneNumber);
        boolean isAdded = memberService.addMember(memberModel);

        if (isAdded) {
            showAlert2("Member added successfully.", Alert.AlertType.INFORMATION);
        } else {
            showAlert2("Failed to add member. Please check your input and try again", Alert.AlertType.WARNING);
        }

        loadTableViewData();
//        setPieChart();
    }

    private void deleteMember() {

        MemberModel selectedMember = tableview.getSelectionModel().getSelectedItem();

        if (selectedMember != null) {

            boolean confirmDelete = showConfirmationDialog("Are you sure you want to delete this Member?");
            if (!confirmDelete) {
                return;
            }

            boolean isDeleted = memberService.deleteMember(selectedMember.getId());

            if (isDeleted) {

                showAlert2("Member deleted successfully.", Alert.AlertType.INFORMATION);
                loadTableViewData();
            } else {
                showAlert2("Failed to delete the Member.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert2("No Member selected to delete", Alert.AlertType.WARNING);
        }
    }

    private void updateMember() {

        if (selectedMember != null) {

            boolean confirmUpdate = showConfirmationDialog("Are you sure you want to update this Member?");
            if (!confirmUpdate) {
                return;
            }

            selectedMember.setFirstName(firstNameField.getText());
            selectedMember.setLastName(lastNameField.getText());
            selectedMember.setGender(genderField.getValue().charAt(0));
            selectedMember.setEmail(emailField.getText());
            selectedMember.setPhoneNumber(phoneNumberField.getText());

            boolean isUpdated = memberService.updateMember(selectedMember);

            if (isUpdated) {
//                tableview.getItems().remove(selectedBook);
                showAlert2("Member updated successfully.", Alert.AlertType.INFORMATION);
                loadTableViewData();
            } else {
                showAlert2("Failed to update the Member.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert2("No Member selected to update", Alert.AlertType.WARNING);
        }
    }

    private boolean showConfirmationDialog(String message) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);

        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }

    private void showAlert1() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("All fields are required. Please fill in every field before proceeding.");
        alert.showAndWait();
    }


    private void showAlert2(String message, Alert.AlertType type) {

        Alert alert = new Alert(type);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearMemberDetails() {

        firstNameField.clear();
        lastNameField.clear();
        genderField.setValue(null);
        emailField.clear();
        phoneNumberField.clear();
    }

    //Pie Chart:
    private void setPieChart() {

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Male", memberService.getMaleMemberCount()),
                new PieChart.Data("Female", memberService.getFemaleMemberCount()));

        pieChartData.forEach(data -> data.nameProperty().bind(
                Bindings.concat(
                        data.getName(), " amount: ", data.pieValueProperty()
                ))
        );

        pieChart.getData().addAll(pieChartData);
    }

}
