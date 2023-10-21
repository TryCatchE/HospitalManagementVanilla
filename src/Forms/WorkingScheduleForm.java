/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Forms;

import Db.Db;
import CommonFuncs.CommonFuncs;
import java.time.LocalTime;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.LocalTimeStringConverter;

/**
 *
 * @author giann
 */
public class WorkingScheduleForm {

    public static void showWorkingScheduleForm(int userId) {
        Stage stage = new Stage();
        stage.setTitle("Select Day of Week and Time");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        Label dayLabel = new Label("Day of Week:");
        gridPane.add(dayLabel, 0, 0);
        ComboBox<String> dayDropdown = new ComboBox<>();
        dayDropdown.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        dayDropdown.setPromptText("Select a day");
        gridPane.add(dayDropdown, 1, 0);

        Label startTimeLabel = new Label("Starting Time: 9:00");
        gridPane.add(startTimeLabel, 0, 1);
        Spinner<LocalTime> startTimeSpinner = new Spinner<>();
        SpinnerValueFactory<LocalTime> startTimeValueFactory = new SpinnerValueFactory<>() {
            {
                setConverter(new LocalTimeStringConverter());
                setValue(LocalTime.of(9, 0));
            }

            @Override
            public void decrement(int steps) {
                setValue(getValue().minusHours(steps));
            }

            @Override
            public void increment(int steps) {
                setValue(getValue().plusHours(steps));
            }
        };
        startTimeValueFactory.setValue(LocalTime.of(9, 0));
        startTimeSpinner.setValueFactory(startTimeValueFactory);
        gridPane.add(startTimeSpinner, 1, 1);

        Label endTimeLabel = new Label("Ending Time: 17:00");
        gridPane.add(endTimeLabel, 0, 2);
        Spinner<LocalTime> endTimeSpinner = new Spinner<>();
        SpinnerValueFactory<LocalTime> endTimeValueFactory = new SpinnerValueFactory<>() {
            {
                setConverter(new LocalTimeStringConverter());
                setValue(LocalTime.of(17, 0));
            }

            @Override
            public void decrement(int steps) {
                setValue(getValue().minusHours(steps));
            }

            @Override
            public void increment(int steps) {
                setValue(getValue().plusHours(steps));
            }
        };
        endTimeValueFactory.setValue(LocalTime.of(17, 0));
        endTimeSpinner.setValueFactory(endTimeValueFactory);
        gridPane.add(endTimeSpinner, 1, 2);

        Button firstSubmitButton = new Button("Submit");
        firstSubmitButton.setOnAction(event -> {
            String selectedDay = dayDropdown.getValue();
            LocalTime selectedStartTime = startTimeSpinner.getValue();
            LocalTime selectedEndTime = endTimeSpinner.getValue();

            if (selectedDay == null || selectedStartTime == null || selectedEndTime == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a day and a starting and ending time.");
                alert.showAndWait();
            } else if (selectedStartTime.isAfter(selectedEndTime)) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Ending time must be after starting time.");
                alert.showAndWait();
            } else {

                addWorkingHours(userId, selectedDay, selectedStartTime, selectedEndTime);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Working hours added.");
                alert.showAndWait();
                stage.close();

            }
        });
        gridPane.add(firstSubmitButton, 1, 3);

        Scene firstScene = new Scene(gridPane, 300, 150);
        stage.setScene(firstScene);
        stage.show();

    }

    private static void addWorkingHours(int id, String selectedDate, LocalTime selectedStartTime, LocalTime selectedEndTime) {

        int convertedDate = CommonFuncs.daysToNumber(selectedDate);

        try {
            Db.getInstance().execute("INSERT INTO slots (doctor_id,working_day, start_time, end_time) VALUES (?,?, ?, ?)", id, convertedDate, selectedStartTime, selectedEndTime);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
