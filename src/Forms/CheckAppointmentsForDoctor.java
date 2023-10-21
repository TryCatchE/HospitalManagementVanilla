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
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author giann
 */
public class CheckAppointmentsForDoctor {

    public static void showCheckAppointments(int userId) {

        Stage mainStage = new Stage();
        mainStage.setTitle("Check Appointments");

        TableView<Appointment> table = new TableView<>();
        table.setEditable(false);

        TableColumn<Appointment, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Appointment, String> nameCol = new TableColumn<>("Client's Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));

        TableColumn<Appointment, String> dayCol = new TableColumn<>("Day");
        dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));

        TableColumn<Appointment, String> hourCol = new TableColumn<>("Hour");
        hourCol.setCellValueFactory(new PropertyValueFactory<>("hour"));

        table.getColumns().addAll(idCol, nameCol, dayCol, hourCol);

        ObservableList<Appointment> data = FXCollections.observableArrayList();

        try {
            
            ResultSet rs = Db.getInstance().query("SELECT a.*, c.name AS client_name FROM appointments a JOIN clients c ON a.client_id = c.id WHERE a.doctor_id=" + userId);

            while (rs.next()) {
                String day = rs.getString("day");
                String hour = rs.getString("hour");
                int id = rs.getInt("id");
                String clientName = rs.getString("client_name");

                data.add(new Appointment(clientName, day, hour, id));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        table.setItems(data);

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));
        borderPane.setCenter(table);

        Scene scene = new Scene(borderPane);
        mainStage.setScene(scene);

        mainStage.show();
    }

}