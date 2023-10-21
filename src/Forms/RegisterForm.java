package Forms;

import Db.Db;
import Models.User;
import CommonFuncs.CommonFuncs;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author giann
 */
public class RegisterForm {

    public static void showRegisterForm(boolean useObservableList, ObservableList<User> data, boolean showRegister) {

        Stage stage = new Stage();
        stage.setTitle("Add User");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        TextField nameField = new TextField();
        nameField.setPromptText("Enter name");

        TextField surnameField = new TextField();
        surnameField.setPromptText("Enter surname");

//        TextField medicalReportField = new TextField();
//        medicalReportField.setPromptText("Enter medical report");
        TextField telField = new TextField();
        telField.setPromptText("Enter telephone number");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");

        Label nameLabel = new Label("Name:");
        Label surnameLabel = new Label("Surname:");
//        Label medicalReportLabelAdd = new Label("Medical Report:");
        Label telLabel = new Label("Tel:");
        Label emailLabel = new Label("Email:");
        Label passwordLabel = new Label("Password:");

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);

        gridPane.add(surnameLabel, 0, 1);
        gridPane.add(surnameField, 1, 1);

//        gridPane.add(medicalReportLabelAdd, 0, 2);
//        gridPane.add(medicalReportField, 1, 2);
        gridPane.add(telLabel, 0, 3);
        gridPane.add(telField, 1, 3);

        gridPane.add(emailLabel, 0, 4);
        gridPane.add(emailField, 1, 4);

        gridPane.add(passwordLabel, 0, 5);
        gridPane.add(passwordField, 1, 5);

        Button saveButton = new Button("Save");

        saveButton.setOnAction(saveEvent -> {
            String newName = nameField.getText();
            String newSurname = surnameField.getText();
//            String newMedicalReport = medicalReportField.getText();
            String newMedicalReport = " ";
            String newTel = telField.getText();
            String newEmail = emailField.getText();
            String newPassword = passwordField.getText();

            if (newName.isEmpty() || newSurname.isEmpty() || newMedicalReport.isEmpty() || newTel.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields.");
                alert.showAndWait();
            } else if (!CommonFuncs.isValidTelephone(newTel)) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid telephone number (numbers only).");
                alert.showAndWait();
            } else if (!CommonFuncs.isValidEmail(newEmail)) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid email address.");
                alert.showAndWait();
            } else if (newPassword.length() < 4) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a password that is at least 4 characters long.");
                alert.showAndWait();
            } else {

                if (useObservableList) {
//                        need encoding
                    User newUser = addUser(newName, newSurname, newTel, newEmail, newMedicalReport, newPassword);
                    if (newUser != null) {
                        data.add(newUser);
                    }
                } else {
//                         needs encoding
                    addUser(newName, newSurname, newTel, newEmail, newMedicalReport, newPassword);
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Registration successful!");
                alert.showAndWait();

                if (showRegister) {

                    ChooseActionForm.showLoginOrRegister("clients");
                }

                stage.close();

            }
        });

        gridPane.add(saveButton, 1, 6);
        Scene scene = new Scene(gridPane, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    private static User addUser(String newName, String newSurname, String newTel, String newEmail, String newMedicalReport, String password) {
        try {
//            String hashedPassword = encodePassword(password);
            Db.getInstance().execute("INSERT INTO clients (name, surname, tel, email, medical_report, password) VALUES (?, ?, ?, ?, ?, ?)", newName, newSurname, newTel, newEmail, newMedicalReport, password);
            return new User(newName, newSurname, newTel, newEmail, newMedicalReport);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
