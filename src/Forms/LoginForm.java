package Forms;

import Db.Db;
import Panels.MainPanel;
import Panels.UserPanel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.ResultSet;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author giann
 */
public class LoginForm {

    public static void showLogin(String userType) {
        Stage registerStage = new Stage();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 0);
        TextField emailTextField = new TextField();
        GridPane.setConstraints(emailTextField, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordField = new PasswordField();
        GridPane.setConstraints(passwordField, 1, 1);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);

        grid.getChildren().addAll(emailLabel, emailTextField, passwordLabel, passwordField, loginButton);

        loginButton.setOnAction(e -> {
            String email = emailTextField.getText();
            String password = passwordField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Required Field");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a value for both fields.");
                alert.showAndWait();
            } else {
                login(email, password, userType);
                registerStage.close();
            }
        });

        Scene scene = new Scene(grid, 300, 150);
        registerStage.setScene(scene);
        registerStage.setTitle("Login Form");
        registerStage.show();
    }


    private static void login(String email, String password, String userType) {
//        String password = decodePassword(encodedPassword);
        String sql = "SELECT * FROM " + userType + " WHERE email = ? AND password = ?";

        ResultSet rs = Db.getInstance().query(sql, email, password);

        try {
            if (rs != null && rs.next()) {

                switch (userType) {
                    case "clients":
                        // code for clients
                        UserPanel.showUserPanel(rs.getInt("id"));
                        break;
                    case "doctors":
                        MainPanel.showMainPanel(rs.getInt("id"));
                        break;
                }
                
            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid email or password.");
                alert.showAndWait();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
