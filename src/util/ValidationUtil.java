package util;

/**
 * Utility class for validating user inputs throughout the application.
 */
public class ValidationUtil {

    public static boolean validateTrainNumber(String trainNumber) {
        // Typically a 5-digit number
        return trainNumber != null && trainNumber.matches("\\d{5}");
    }

    public static boolean validateStationCode(String stationCode) {
        // Typically 3 to 5 capital alphabets
        return stationCode != null && stationCode.matches("[A-Z]{3,5}");
    }

    public static boolean validateUsername(String username) {
        return username != null && username.length() >= 4;
    }

    public static boolean validatePassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean validateAge(int age) {
        return age >= 0 && age <= 120;
    }
}
