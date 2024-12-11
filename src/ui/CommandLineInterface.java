package ui;

import config.Configuration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import logging.Logger;
import java.io.*;
import java.util.Scanner;

public class CommandLineInterface {
    // Path to the configuration file
    private static final String CONFIG_FILE = "resource/configuration.json";

    // Gson instance for JSON serialization and deserialization
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Initializes the system configuration, either from a saved file or user input
    public static Configuration initializeFromCLI() {
        Scanner scanner = new Scanner(System.in);
        Logger.log("-----------------------------------");
        Logger.log("Welcome to Ticket Management System");
        Logger.log("-----------------------------------");

        // Check if a previous configuration exists
        Configuration previousConfig = loadConfiguration();
        if (previousConfig != null) {
            System.out.println("A saved configuration was found:");
            System.out.println(previousConfig);
            System.out.print("Do you want to use this configuration? (yes/no): ");
            String userInput = scanner.nextLine().trim().toLowerCase();

            if (userInput.equals("yes")) {
                Logger.log("Using the saved configuration.");
                return previousConfig; // Return the loaded configuration
            }
        }

        // If no configuration exists or user selects "no", prompt for new inputs
        Logger.log("Starting system configuration...");
        int totalTickets = readPositiveInteger(scanner, "Enter the total number of tickets: ");
        int ticketReleaseRate = readPositiveInteger(scanner, "Enter the ticket release rate (in seconds): ");
        int customerRetrievalRate = readPositiveInteger(scanner, "Enter the customer retrieval rate (in seconds): ");
        int maxTicketCapacity;
        while (true) {
            maxTicketCapacity = readPositiveInteger(scanner, "Enter the maximum ticket pool capacity: ");
            if (maxTicketCapacity <= totalTickets) {
                break;
            } else {
                System.out.println("The maximum ticket pool capacity cannot be greater than the total number of tickets;");
                System.out.println("Please, Enter the maximum ticket pool capacity: ");
            }
        }
        int numberOfVendors = readPositiveInteger(scanner, "Enter number of vendors: ");
        int numberOfCustomers = readPositiveInteger(scanner, "Enter number of customers: ");

        // Create new configuration and save it
        Configuration newConfig = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity, numberOfVendors, numberOfCustomers);
        saveConfiguration(newConfig); // Save the new configuration for future use
        Logger.log("Configuration has been saved.");
        return newConfig;
    }

    // Reads a positive integer from the user with validation
    private static int readPositiveInteger(Scanner scanner, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value > 0) {
                    return value; // Return valid positive integer
                } else {
                    System.out.println("Please enter a positive integer.");
                }
            } else {
                System.out.println("Invalid input. Please enter a positive integer.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    // Loads a configuration from the JSON file
    private static Configuration loadConfiguration() {
        try (Reader reader = new FileReader(CONFIG_FILE)) {
            return gson.fromJson(reader, Configuration.class); // Deserialize JSON to Configuration object
        } catch (FileNotFoundException e) {
            System.out.println("No existing configuration file found.");
            return null; // Return null if file doesn't exist
        } catch (IOException e) {
            System.err.println("Failed to load configuration: " + e.getMessage());
            return null;
        }
    }

    // Saves a configuration to the JSON file
    private static void saveConfiguration(Configuration config) {
        try (Writer writer = new FileWriter(CONFIG_FILE)) {
            gson.toJson(config, writer); // Serialize Configuration object to JSON
            System.out.println("Configuration saved to " + CONFIG_FILE);
        } catch (IOException e) {
            System.err.println("Failed to save configuration: " + e.getMessage());
        }
    }
}

