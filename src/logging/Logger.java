package logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {
    // Log file path
    private final static String LOG_FILE = "resource/log.txt";

    // Logs a general message to console and file
    public static synchronized void log(String message) {
        String timeStampedMessage = LocalDateTime.now() + ": " + message; // Add timestamp
        System.out.println(timeStampedMessage); // Print to console
        writeToFile(timeStampedMessage, true); // Write to file
    }

    // Clears the log file
    public static synchronized void clearLogFile() {
        writeToFile("", false); // Overwrite file
    }

    // Writes a message to the log file
    private static void writeToFile(String message, boolean append) {
        File file = new File(LOG_FILE); // Log file
        try {
            if (!file.exists()) { // Create file if not exists
                file.getParentFile().mkdirs(); // Create parent directories
                file.createNewFile(); // Create the file
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, append))) {
                if (!message.isEmpty()) {
                    bw.write(message); // Write the message
                    bw.newLine(); // New line after message
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage()); // Error message
        }
    }
}




