/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Forms;

import Db.Db;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author giann
 */
public class CreateMedicalHistory {

    public static void showFormMedicalReport(int doctorId, int clientId) {
        Stage mainStage = new Stage();

        Label historyLabel = new Label("Medical history:");

        TextArea textArea = new TextArea();
//    textArea.setEditable(false);

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(historyLabel, textArea);

        Button saveButton = new Button("Save");
        
        saveButton.setOnAction(event -> {
            try {
                String newHistory = textArea.getText();
                Db.getInstance().execute("UPDATE medical_history SET medical_history = ? WHERE client_id = ? AND doctor_id = ?", newHistory, clientId, doctorId);
                
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Medical history updated successfully!");
                alert.showAndWait();
                mainStage.close();
                
            } catch (Exception e) {
                
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("An error occurred while updating the medical history");
                alert.showAndWait();
                
            }
        });

        HBox hbox = new HBox(saveButton);
        hbox.setAlignment(Pos.CENTER_RIGHT);
        hbox.setPadding(new Insets(10));
        vbox.getChildren().add(hbox);

        try {
            
//            todo ids
            ResultSet rs = Db.getInstance().query("SELECT medical_history FROM medical_history WHERE client_id = ? AND doctor_id = ?", clientId, doctorId);
            if (rs.next()) {
                String medicalHistory = rs.getString("medical_history");
                textArea.setText(medicalHistory);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(vbox, 400, 300);
        mainStage.setScene(scene);
        mainStage.setTitle("Medical Report");
        mainStage.show();
    }
}
