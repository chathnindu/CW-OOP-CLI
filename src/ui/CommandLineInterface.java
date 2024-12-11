package ui;

import config.Configuration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import logging.Logger;

import java.io.*;
import java.util.Scanner;

public class CommandLineInterface {
    private static final String CONFIG_FILE = "resource/configuration.json"; // Path to config file
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Gson instance

    public static Configuration initializeFromCLI() {
        Scanner scanner = new Scanner(System.in);
        Logger.log("-----------------------------------");
        Logger.log("Welcome to Ticket Management System");
        Logger.log("-----------------------------------");

        // Load saved configuration if it exists
        Configuration previousConfig = loadConfiguration();
        if (previousConfig != null) {
            System.out.println("A saved configuration was found:");
            System.out.println(previousConfig);
            System.out.print("Do you want to use this configuration? (yes/no): ");
            String userInput = scanner.nextLine().trim().toLowerCase();

            if (userInput.equals("yes")) {
                Logger.log("Using the saved configuration.");
                return previousConfig; // Use the saved configuration
            }
        }

        // Prompt user for new configuration inputs
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
                System.out.println("The maximum ticket pool capacity cannot exceed the total number of tickets.");
            }
        }

        int numberOfVendors = readPositiveInteger(scanner, "Enter number of vendors: ");
        int numberOfCustomers = readPositiveInteger(scanner, "Enter number of customers: ");

        int numberOfVipCustomers;
        while (true) {
            numberOfVipCustomers = readPositiveInteger(scanner, "Enter number of VIP customers: ");
            if (numberOfVipCustomers <= numberOfCustomers) {
                break;
            } else {
                System.out.println("The number of VIP customers cannot exceed the total number of customers.");
            }
        }

        // Save and return the new configuration
        Configuration newConfig = new Configuration(
                totalTickets, ticketReleaseRate, customerRetrievalRate,
                maxTicketCapacity, numberOfVendors, numberOfCustomers, numberOfVipCustomers
        );
        saveConfiguration(newConfig);
        Logger.log("Configuration has been saved.");
        return newConfig;
    }

    private static int readPositiveInteger(Scanner scanner, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value > 0) {
                    return value;
                } else {
                    System.out.println("Please enter a positive integer.");
                }
            } else {
                System.out.println("Invalid input. Please enter a positive integer.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    private static Configuration loadConfiguration() {
        try (Reader reader = new FileReader(CONFIG_FILE)) {
            return gson.fromJson(reader, Configuration.class);
        } catch (FileNotFoundException e) {
            System.out.println("No existing configuration file found.");
            return null;
        } catch (IOException e) {
            System.err.println("Failed to load configuration: " + e.getMessage());
            return null;
        }
    }

    private static void saveConfiguration(Configuration config) {
        try (Writer writer = new FileWriter(CONFIG_FILE)) {
            gson.toJson(config, writer);
            System.out.println("Configuration saved to " + CONFIG_FILE);
        } catch (IOException e) {
            System.err.println("Failed to save configuration: " + e.getMessage());
        }
    }
}

