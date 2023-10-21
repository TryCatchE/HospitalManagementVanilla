/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Forms;

import Db.Db;
import Models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author giann
 */
public class MedicalReport {

    public static void showFormMedicalReport(int clientId) {
        Stage mainStage = new Stage();
        

        Label historyLabel = new Label("Medical history:");
        Label chooseDoctor = new Label("Choose doctor's name:");

        ComboBox<String> dropdown = new ComboBox<>();

        List<User> doctors = getNamesFromDatabase(clientId);
        List<String> doctorNames = doctors.stream().map(User::getName).collect(Collectors.toList());
        dropdown.getItems().addAll(doctorNames);

        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        dropdown.setOnAction(e -> {
            String selectedName = dropdown.getSelectionModel().getSelectedItem();
            String medicalHistory = getMedicalHistoryFromDatabase(selectedName);
            textArea.setText(medicalHistory);
        });

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(chooseDoctor, dropdown, historyLabel, textArea);

        Scene scene = new Scene(vbox, 400, 300);
        mainStage.setScene(scene);
        mainStage.setTitle("Medical Report");
        mainStage.show();
    }

    private static List<User> getNamesFromDatabase(int userId) {
        List<User> doctors = new ArrayList<>();

        try {
            ResultSet rs = Db.getInstance().query("SELECT m.*, d.name AS name FROM medical_history m INNER JOIN doctors d ON m.doctor_id = d.id WHERE m.client_id = ?", userId);
            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                User doctor = new User(name, id);
                doctors.add(doctor);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctors;
    }

    private static String getMedicalHistoryFromDatabase(String doctorName) {
        String medicalHistory = "";

        try {
            ResultSet rs = Db.getInstance().query("SELECT medical_history FROM medical_history WHERE doctor_id IN (SELECT id FROM doctors WHERE name = ?)", doctorName);

            if (rs.next()) {
                medicalHistory = rs.getString("medical_history");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicalHistory;
    }

}
