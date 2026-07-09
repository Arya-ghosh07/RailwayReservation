package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Helper class for parsing, formatting, and manipulating date and time values.
 */
public class DateTimeUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String getCurrentDateString() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    public static String getCurrentTimestampString() {
        return LocalDateTime.now().format(TIMESTAMP_FORMATTER);
    }

    public static boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr, DATE_FORMATTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int parseTimeToMinutes(String timeStr) {
        // e.g., "14:30" -> 14 * 60 + 30 = 870 minutes
        // Placeholder implementation
        return 0;
    }

    public static String formatMinutesToTime(int totalMinutes) {
        // e.g., 870 -> "14:30"
        // Placeholder implementation
        return "";
    }
}
