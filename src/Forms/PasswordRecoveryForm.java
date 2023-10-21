package Forms;

import Db.Db;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PasswordRecoveryForm {

    public static void showPasswordRecoveryForm(int clientId) {

        Stage passwordRecoveryStage = new Stage();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        Label newPasswordLabel = new Label("New Password:");
        GridPane.setConstraints(newPasswordLabel, 0, 0);
        PasswordField newPasswordField = new PasswordField();
        GridPane.setConstraints(newPasswordField, 1, 0);

        Label confirmPasswordLabel = new Label("Confirm New Password:");
        GridPane.setConstraints(confirmPasswordLabel, 0, 1);
        PasswordField confirmPasswordField = new PasswordField();
        GridPane.setConstraints(confirmPasswordField, 1, 1);

        Button saveButton = new Button("Save");
        GridPane.setConstraints(saveButton, 1, 2);

        grid.getChildren().addAll(newPasswordLabel, newPasswordField, confirmPasswordLabel, confirmPasswordField, saveButton);

        saveButton.setOnAction(e -> {
            String newPassword = newPasswordField.getText();
            String confirmedPassword = confirmPasswordField.getText();

            if (newPassword.length() < 4) {
                
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Password");
                alert.setHeaderText(null);
                alert.setContentText("Password should be at least 4 characters long.");
                alert.showAndWait();
                
            } else if (!newPassword.equals(confirmedPassword)) {
                
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Passwords do not match");
                alert.setHeaderText(null);
                alert.setContentText("The passwords you entered do not match. Please try again.");
                alert.showAndWait();
                
            } else {
                
                String newPass = newPasswordField.getText();
                

                updatePassword(newPass, clientId);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Password Changed");
                alert.setHeaderText(null);
                alert.setContentText("Your password has been changed successfully.");
                alert.showAndWait();
                passwordRecoveryStage.close();
                
            }
        });

        Scene scene = new Scene(grid, 300, 150);
        passwordRecoveryStage.setScene(scene);
        passwordRecoveryStage.setTitle("Change Password");
        passwordRecoveryStage.show();
    }

    private static void updatePassword(String newPassword, int clientId) {

        try {
            Db.getInstance().execute("UPDATE clients SET password=? WHERE id=?", newPassword, clientId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
