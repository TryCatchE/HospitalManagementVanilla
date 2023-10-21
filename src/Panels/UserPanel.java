/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Panels;

import Db.Db;
import Models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import CommonFuncs.CommonFuncs;
import Forms.CheckAppointments;
import Models.UserIdContainer;
import java.util.HashSet;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import Forms.PasswordRecoveryForm;
import Forms.MedicalReport;

/**
 *
 * @author giann
 */
public class UserPanel {

    public static void showUserPanel(int clientId) {

        Stage primaryStage = new Stage();

        BorderPane root = new BorderPane();
        UserIdContainer container = new UserIdContainer();

        Button changePassword = new Button("Change Password");
        Button checkAppointments = new Button("Check Appointments");
        Button viewMedicalReport = new Button("Medical Treatments");
        
        
        changePassword.setOnAction(e->{
            
            PasswordRecoveryForm.showPasswordRecoveryForm(clientId);
            
        });
        
        viewMedicalReport.setOnAction(e->{
            MedicalReport.showFormMedicalReport(clientId);
        });

        HBox searchBar = new HBox(changePassword, checkAppointments, viewMedicalReport);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.setSpacing(10);
        root.setTop(searchBar);

        ComboBox<String> specBox = new ComboBox<>();
        specBox.setPromptText("Specialization");
        specBox.setPrefWidth(200);

        ComboBox<String> nameBox = new ComboBox<>();
        nameBox.setPromptText("Doctor's Name");
        nameBox.setPrefWidth(200);
        nameBox.setVisible(false);

        ComboBox<String> hoursBox = new ComboBox<>();
        hoursBox.setPromptText("Available Hours");
        hoursBox.setPrefWidth(200);
        hoursBox.setVisible(false);

        ComboBox<String> workingDays = new ComboBox<>();
        workingDays.setPromptText("Available Days");
        workingDays.setPrefWidth(200);
        workingDays.setVisible(false);

//        Label specLabel = new Label("Specialization:");
//        Label nameLabel = new Label("Doctor's Name:");
//        Label daysLabel = new Label("Available Days:");
//        Label hoursLabel = new Label("Available Hours:");
        VBox centerBox = new VBox(specBox, nameBox, workingDays, hoursBox);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(10);
        root.setCenter(centerBox);

        Label makeAppointmentLabel = new Label("Make an Appointment");
        centerBox.getChildren().add(0, makeAppointmentLabel);

//        Label test = new Label("Make an Appointment");
//        centerBox.getChildren().add(0, test);
        Label resultCountLabel = new Label();
        root.setBottom(resultCountLabel);
        BorderPane.setMargin(resultCountLabel, new Insets(5));

        specBox.getItems().addAll(getAll());
        specBox.setOnAction(event -> {
            if (specBox.getValue() != null) {
                String specialization = specBox.getSelectionModel().getSelectedItem();

                List<User> doctors = getDoctorsBySpec(specialization);
                List<String> doctorNames = doctors.stream().map(User::getName).collect(Collectors.toList());
                nameBox.setItems(FXCollections.observableArrayList(doctorNames));
                nameBox.setVisible(true);

                nameBox.setOnAction(event2 -> {
                    String selectedDoctorName = nameBox.getSelectionModel().getSelectedItem();

                    Optional<User> selectedDoctor = doctors.stream().filter(d -> d.getName().equals(selectedDoctorName)).findFirst();
                    int doctorId = selectedDoctor.get().getId();
                    container.setUserId(doctorId);

                    ObservableList<String> availableDays = getAvailableDays(doctorId);
                    workingDays.setItems(availableDays);
                    workingDays.setVisible(!availableDays.isEmpty());
                    hoursBox.setVisible(false);

                    workingDays.setOnAction(event3 -> {
                        int selectedDay = CommonFuncs.daysToNumber(workingDays.getSelectionModel().getSelectedItem());
                        ObservableList<String> availableHours = getAvailableHours(doctorId, selectedDay);
                        hoursBox.setItems(availableHours);
                        hoursBox.setVisible(!availableHours.isEmpty());
                    });
                });
            } else {
                nameBox.setVisible(false);
                workingDays.setVisible(false);
                hoursBox.setVisible(false);
            }
        });

        checkAppointments.setOnAction(e -> {

            CheckAppointments.showCheckAppointments(clientId);
        });

        Button submitBtn = new Button("Submit");
        HBox bottomBar = new HBox(submitBtn);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setSpacing(10);
        root.setBottom(bottomBar);

        submitBtn.setOnAction(event -> {
            String doctorName = nameBox.getSelectionModel().getSelectedItem();
            String hour = hoursBox.getSelectionModel().getSelectedItem();
            String day = workingDays.getSelectionModel().getSelectedItem();
            String spec = specBox.getSelectionModel().getSelectedItem();
            int doctorId = container.getUserId();
            

            saveAppointment(doctorId, clientId, doctorName, day, hour, spec);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Appointment saved");
            alert.setHeaderText(null);
            alert.setContentText("Your appointment with " + doctorName + " on " + day + " at " + hour + " has been saved.");
            alert.showAndWait();
        });

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static List<User> getDoctorsBySpec(String specialazition) {
        List<User> doctors = new ArrayList<>();

        try {
            ResultSet rs = Db.getInstance().query("SELECT name, id FROM doctors WHERE specialazition LIKE ?", "%" + specialazition + "%");
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

    private static ObservableList<String> getAvailableDays(int doctorId) {
        ObservableList<String> availableDays = FXCollections.observableArrayList();

        try {
            ResultSet rs = Db.getInstance().query("SELECT  working_day FROM slots WHERE doctor_id = ?", doctorId);

            while (rs.next()) {
                int day = rs.getInt("working_day");

                String date = CommonFuncs.numberToDay(day);

                availableDays.add(date);
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        HashSet<String> dedupeDays = new HashSet<>(availableDays);
        ObservableList<String> uniqueDays = FXCollections.observableArrayList(dedupeDays);

        return uniqueDays;
    }

    private static ObservableList<String> getAvailableHours(int doctorId, int workingDay) {
        ObservableList<String> availableHours = FXCollections.observableArrayList();

        try {
            ResultSet rs = Db.getInstance().query("SELECT  start_time, end_time FROM  slots WHERE doctor_id = ? AND working_day= ?", doctorId, workingDay);

            while (rs.next()) {
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                LocalTime start = LocalTime.parse(startTime);
                LocalTime end = LocalTime.parse(endTime);

                while (start.isBefore(end)) {
                    availableHours.add(start.format(DateTimeFormatter.ofPattern("h:mm a")));
                    start = start.plusMinutes(30);
                }
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableHours;
    }

    private static ObservableList<String> getAll() {
        ObservableList<String> specializations = FXCollections.observableArrayList();
        try {
            ResultSet rs = Db.getInstance().query("SELECT specialazition FROM doctors");
            while (rs.next()) {
                String specialization = rs.getString("specialazition");
                specializations.add(specialization);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specializations;
    }

    private static void saveAppointment(int doctorId, int clientId, String doctorsName, String day, String hour, String specialization) {

        try {
            Db.getInstance().execute("INSERT INTO appointments (doctor_id, client_id,doctors_name, day, hour, specialization) VALUES (?, ?, ?, ?, ?, ?)", doctorId, clientId, doctorsName, day, hour, specialization);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
