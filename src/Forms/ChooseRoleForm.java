package Forms;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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
public class ChooseRoleForm {

    public static void showChooseRoleForm() {

        Stage registerStage = new Stage();

        RadioButton clientRadioButton = new RadioButton("Client");
        RadioButton doctorRadioButton = new RadioButton("Doctor");

        ToggleGroup toggleGroup = new ToggleGroup();
        clientRadioButton.setToggleGroup(toggleGroup);
        doctorRadioButton.setToggleGroup(toggleGroup);

        Button nextButton = new Button("Next");

        nextButton.setOnAction(event -> {
            if (clientRadioButton.isSelected()) {

                String userType = "clients";

                ChooseActionForm.showLoginOrRegister(userType);

            } else if (doctorRadioButton.isSelected()) {
                String userType = "doctors";
                ChooseActionForm.showLoginOrRegister(userType);

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select an option.");
                alert.showAndWait();
            }
            registerStage.close(); 
        });

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        root.getChildren().addAll(clientRadioButton, doctorRadioButton, nextButton);

        Scene scene = new Scene(root, 300, 200);

        registerStage.setScene(scene);
        registerStage.show();
    }
}
