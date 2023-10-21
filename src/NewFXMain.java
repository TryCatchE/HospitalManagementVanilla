/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */

import Forms.ChooseRoleForm;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author giann
 */
public class NewFXMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        ChooseRoleForm.showChooseRoleForm();
   
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
