package Forms;

import Panels.MainPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author giann
 */
public class ChooseActionForm {

    public static void showLoginOrRegister(String userType) {
        Stage primaryStage = new Stage();

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {

            LoginForm.showLogin(userType);
            primaryStage.close();

        });

        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> {

            
            primaryStage.close();

            switch (userType) {
                case "clients":
                    RegisterForm.showRegisterForm(false, null, true);
                    break;
                case "doctors":
                   DoctorRegisterForm.showRegisterForm();
                    break;
            }

        });

        HBox buttonContainer = new HBox(10, loginButton, registerButton);
        buttonContainer.setAlignment(Pos.CENTER);

        VBox root = new VBox(20, new Label("Choose an action:"), buttonContainer);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login or Register");
        primaryStage.show();
    }
}
