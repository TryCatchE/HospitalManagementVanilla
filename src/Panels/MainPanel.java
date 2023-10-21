package Panels;

import Db.Db;
import Forms.RegisterForm;
import Forms.WorkingScheduleForm;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import Forms.CheckAppointmentsForDoctor;
import Forms.CreateMedicalHistory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author giann
 */
//fix bug with textFIeld
public class MainPanel {

    public static void showMainPanel(int userId) {

        Stage mainStage = new Stage();
        mainStage.setTitle("Main Panel");
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        mainStage.setWidth(screenBounds.getWidth());
        mainStage.setHeight(screenBounds.getHeight());

        mainStage.show();

        TextField searchField = new TextField();
        Label searchLabel = new Label("Search Clients...");
        searchField.setPromptText("Search by name");

        TextArea queryArea = new TextArea();
        queryArea.setEditable(true);
        queryArea.setPromptText("Medical Report Results");
        Label medicalReportLabel = new Label("Medical Report");

        TableView<User> table = new TableView<>();
        table.setEditable(false);

        TableColumn<User, String> clientId = new TableColumn<>("id");
        TableColumn<User, String> nameCol = new TableColumn<>("Name");
        TableColumn<User, String> usernameCol = new TableColumn<>("Surname");
        TableColumn<User, String> medicalReport = new TableColumn<>("Medical Diagnosis");
        TableColumn<User, String> email = new TableColumn<>("Email");
        TableColumn<User, String> tel = new TableColumn<>("tel");

        table.getColumns().addAll(clientId, nameCol, usernameCol, tel, email, medicalReport);

        ObservableList<User> data = FXCollections.observableArrayList();

        try {
            ResultSet rs = Db.getInstance().query("SELECT * FROM clients");
            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String telNum = rs.getString("tel");
                String emailAddr = rs.getString("email");
                String medicalReportText = rs.getString("medical_report");
                int id = rs.getInt("id");

                data.add(new User(id, name, surname, telNum, emailAddr, medicalReportText));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        clientId.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        medicalReport.setCellValueFactory(new PropertyValueFactory<>("medicalReport"));

        table.setItems(data);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            data.clear();
            if (newValue != null && !newValue.isEmpty()) {
                searchResult(newValue, table);
            }
        });

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                queryArea.setText(newSelection.getMedicalReport());
            }
        });

        Button addButton = new Button("Add");

        addButton.setOnAction(event -> {

            RegisterForm.showRegisterForm(true, data, false);

        });

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(event -> {

            User selectedUser = table.getSelectionModel().getSelectedItem();

            if (selectedUser != null) {
                data.remove(selectedUser);
                deleteUser(selectedUser);
            }
        });

        Button getAllBtn = new Button("All Clients");
        getAllBtn.setOnAction(event -> {
            getAll(nameCol, usernameCol, tel, email, medicalReport, table);
        });

        Button saveButton = new Button("Save Changes");
        saveButton.setOnAction(e -> {
            User selectedUser = table.getSelectionModel().getSelectedItem();
            String newMedicalReport = queryArea.getText();

            if (selectedUser != null) {
                updateUser(selectedUser, newMedicalReport);

                selectedUser.setMedicalReport(newMedicalReport);
                table.refresh();
            }
        });

        Button workingHours = new Button("Add Working Hours");

        Button checkAppointments = new Button("Check your appointments");

        Button medicalTreatment = new Button("Create Medical Treatment");

        workingHours.setOnAction(event -> {
            WorkingScheduleForm.showWorkingScheduleForm(userId);
        });

        checkAppointments.setOnAction(event -> {
            CheckAppointmentsForDoctor.showCheckAppointments(userId);
        });

        medicalTreatment.setOnAction(e -> {
            User selectedUser = table.getSelectionModel().getSelectedItem();
            if (selectedUser == null) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("No User Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please choose a user from the list.");
                alert.showAndWait();
            } else {
                int selectedClientId = selectedUser.getId();
                CreateMedicalHistory.showFormMedicalReport(userId, selectedClientId);
            }
        });

        GridPane buttonsPane = new GridPane();
        buttonsPane.setHgap(10);
        buttonsPane.setVgap(10);
        buttonsPane.setPadding(new Insets(10));
        buttonsPane.setAlignment(Pos.CENTER);

        buttonsPane.add(addButton, 0, 0);
        buttonsPane.add(deleteButton, 1, 0);
        buttonsPane.add(saveButton, 2, 0);
        buttonsPane.add(getAllBtn, 2, 3);
        buttonsPane.add(workingHours, 4, 0);
        buttonsPane.add(checkAppointments, 3, 0);
        buttonsPane.add(medicalTreatment, 3, 3);

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));
        borderPane.setLeft(new VBox(searchLabel, searchField, medicalReportLabel, queryArea, buttonsPane));
        borderPane.setCenter(table);

        Scene scene = new Scene(borderPane, 600, 400);
        mainStage.setScene(scene);
        mainStage.setTitle("User Query");
        mainStage.show();
    }

    private static void searchResult(String searchValue, TableView<User> table) {

        ObservableList<User> data = FXCollections.observableArrayList();
        try {
            ResultSet rs = Db.getInstance().query("SELECT * FROM clients WHERE name LIKE ?", "%" + searchValue + "%");
            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String telNum = rs.getString("tel");
                String emailAddr = rs.getString("email");
                String medicalReportText = rs.getString("medical_report");
                int id = rs.getInt("id");

                data.add(new User(id, name, surname, telNum, emailAddr, medicalReportText));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.setItems(data);

    }

    private static void getAll(TableColumn<User, String> nameCol, TableColumn<User, String> usernameCol,
            TableColumn<User, String> tel, TableColumn<User, String> email,
            TableColumn<User, String> medicalReport, TableView<User> table) {

        ObservableList<User> data = FXCollections.observableArrayList();
        try {
            ResultSet rs = Db.getInstance().query("SELECT * FROM clients ");
            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String telNum = rs.getString("tel");
                String emailAddr = rs.getString("email");
                String medicalReportText = rs.getString("medical_report");
                int id = rs.getInt("id");

                data.add(new User(id, name, surname, telNum, emailAddr, medicalReportText));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        medicalReport.setCellValueFactory(new PropertyValueFactory<>("medicalReport"));

        table.setItems(data);
    }

    private static void deleteUser(User user) {

        try {
            Db.getInstance().execute("DELETE FROM clients WHERE id=" + user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateUser(User user, String newMedicalReport) {
        if (!newMedicalReport.isEmpty()) {
            try {
                Db.getInstance().execute("UPDATE clients SET medical_report=? WHERE id=?", newMedicalReport, user.getId());
                user.setMedicalReport(newMedicalReport);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
