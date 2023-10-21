package Models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author giann
 */
public class User {

    private String name;
    private String surname;
    private String email;
    private String password;
    private String tel;
    private String medicalReport;
    private String role;
    private int id;

    public User(String name, String surname, String email, String password, String tel, String medicalReport, String role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.tel = tel;
        this.medicalReport = medicalReport;
        this.role = role;
    }

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String email) {
        this.email = email;
    }

    public User(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public User(String name, String surname, String tel, String email, String medicalReport) {
        this.name = name;
        this.tel = tel;
        this.email = email;
        this.medicalReport = medicalReport;
    }

    public User(int id, String name, String surname, String tel, String email, String medicalReport) {
        this.name = name;
        this.id = id;
        this.tel = tel;
        this.email = email;
        this.medicalReport = medicalReport;
    }

    public void clear() {
        this.name = "";
        this.surname = "";
        this.email = "";
        this.password = "";
        this.tel = "";
        this.medicalReport = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMedicalReport() {
        return medicalReport;
    }

    public void setMedicalReport(String medicalReport) {
        this.medicalReport = medicalReport;
    }

    public String getRole() {
        return role;
    }

    public int getId() {
        return id;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
