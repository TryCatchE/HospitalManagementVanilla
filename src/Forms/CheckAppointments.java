/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Forms;

import Db.Db;
import Models.Appointment;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author giann
 */
public class CheckAppointments {

    public static void showCheckAppointments(int clientId) {

        Stage mainStage = new Stage();
        mainStage.setTitle("Check Appointments");

        TableView<Appointment> table = new TableView<>();
        table.setEditable(false);

        TableColumn<Appointment, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Appointment, String> nameCol = new TableColumn<>("Doctor's Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("doctorName"));

        TableColumn<Appointment, String> dayCol = new TableColumn<>("Day");
        dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));

        TableColumn<Appointment, String> hourCol = new TableColumn<>("Hour");
        hourCol.setCellValueFactory(new PropertyValueFactory<>("hour"));

        TableColumn<Appointment, String> specCol = new TableColumn<>("Specialization");
        specCol.setCellValueFactory(new PropertyValueFactory<>("specialization"));

        table.getColumns().addAll(idCol, nameCol, dayCol, hourCol, specCol);

        ObservableList<Appointment> data = FXCollections.observableArrayList();

        try {

            ResultSet rs = Db.getInstance().query("SELECT * FROM appointments WHERE client_id=" + clientId);
            while (rs.next()) {
                String name = rs.getString("doctors_name");
                String day = rs.getString("day");
                String hour = rs.getString("hour");
                String spec = rs.getString("specialization");
                int id = rs.getInt("id");
                data.add(new Appointment(name, day, hour, spec, id));

            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.setItems(data);

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(event -> {
            Appointment selectedAppointment = table.getSelectionModel().getSelectedItem();
            if (selectedAppointment != null) {
                data.remove(selectedAppointment);
                try {
                    int appointmentId = selectedAppointment.getId();
                    Db.getInstance().execute("DELETE FROM appointments WHERE id=" + appointmentId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        HBox buttonBar = new HBox();
        buttonBar.setAlignment(Pos.CENTER_RIGHT);
        buttonBar.setSpacing(10);
        buttonBar.setPadding(new Insets(10));
        buttonBar.getChildren().add(deleteButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));
        borderPane.setCenter(table);
        borderPane.setBottom(buttonBar);

        Scene scene = new Scene(borderPane);
        mainStage.setScene(scene);

        mainStage.show();
    }

}
