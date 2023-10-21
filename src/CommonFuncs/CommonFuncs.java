/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CommonFuncs;

/**
 *
 * @author giann
 */
public class CommonFuncs {

    public static int daysToNumber(String selectedDate) {

        int dateToNumber = 0;

        switch (selectedDate) {
            case "Monday":
                dateToNumber = 1;
                break;
            case "Tuesday":
                dateToNumber = 2;
                break;
            case "Wednesday":
                dateToNumber = 3;
                break;
            case "Thursday":
                dateToNumber = 4;
                break;
            case "Friday":
                dateToNumber = 5;
                break;
            case "Saturday":
                dateToNumber = 6;
                break;
            case "Sunday":
                dateToNumber = 7;
                break;
        }

        return dateToNumber;
    }

    public static String numberToDay(int dayNumber) {
        String dayName = "";

        switch (dayNumber) {
            case 1:
                dayName = "Monday";
                break;
            case 2:
                dayName = "Tuesday";
                break;
            case 3:
                dayName = "Wednesday";
                break;
            case 4:
                dayName = "Thursday";
                break;
            case 5:
                dayName = "Friday";
                break;
            case 6:
                dayName = "Saturday";
                break;
            case 7:
                dayName = "Sunday";
                break;
        }

        return dayName;
    }

    public static String decodePassword(String encodedPassword) {
        String password = "";
        for (int i = 0; i < encodedPassword.length(); i++) {
            char c = encodedPassword.charAt(i);
            c -= 10;
            password += c;
        }
        return password;
    }

    public static String encodePassword(String password) {

        String encodedPassword = "";
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            c += 10;
            encodedPassword += c;
        }
        return encodedPassword;
    }
    
    
        public static boolean isValidTelephone(String telephone) {
        return telephone.matches("\\d+");
    }

    public  static boolean isValidEmail(String email) {
        String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        return email.matches(emailPattern);
    }

}
