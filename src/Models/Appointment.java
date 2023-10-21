/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author giann
 */
public class Appointment {

    int id;
//    int doctor_id;
//    int client_id;
    String doctorName;
    String day;
    String hour;
    String specialization;
    String clientName;

    public Appointment(String doctorName, String hour, String day, String specialization, int id) {
        this.doctorName = doctorName;
        this.hour = hour;
        this.day = day;
        this.specialization = specialization;
        this.id = id;
    }

    public Appointment(String clientName, String hour, String day, int id) {
        this.clientName = clientName;
        this.hour = hour;
        this.day = day;
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

}
