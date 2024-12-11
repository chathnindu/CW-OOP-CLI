import config.Configuration;
import core.TicketPool;
import logging.Logger;
import threads.Customer;
import threads.Vendor;
import ui.CommandLineInterface;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Initialize the system configuration from the command-line interface
        Configuration config = CommandLineInterface.initializeFromCLI();

        // Create a ticket pool with the specified maximum capacity
        TicketPool ticketPool = new TicketPool(config.getMaximumTicketCapacity());

        // Clear log file at the start of the session
        Logger.clearLogFile();

        // Create Vendor Threads
        Thread[] vendorThreads = new Thread[config.getNumberOfVendors()];
        for (int i = 0; i < config.getNumberOfVendors(); i++) {
            vendorThreads[i] = new Thread(new Vendor(ticketPool, config.getReleaseRate(), config.getTotalTickets()));
            vendorThreads[i].setName("Vendor-" + i); // Set the name of the vendor thread
            vendorThreads[i].start(); // Start the vendor thread
        }

        // Create Customer Threads
        Thread[] customerThreads = new Thread[config.getNumberOfCustomers()];
        for (int i = 0; i < config.getNumberOfCustomers(); i++) {
            customerThreads[i] = new Thread(new Customer(ticketPool, config.getRetrievalRate(), config.getTotalTickets()));
            customerThreads[i].setName("Customer-" + i); // Set the name of the customer thread
            customerThreads[i].start(); // Start the customer thread
        }

        // Command Listener Thread to listen for the stop command
        Thread commandListener = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String command = scanner.nextLine().trim(); // Read the user input
                if (command.equalsIgnoreCase("stop")) {
                    ticketPool.setStopFlag(true); // Set stop flag to stop all threads
                    System.out.println("Stop command received. Shutting down...");

                    // Interrupt all vendor and customer threads to wake them up
                    for (Thread vendor : vendorThreads) {
                        vendor.interrupt();
                    }
                    for (Thread customer : customerThreads) {
                        customer.interrupt();
                    }
                    break; // Exit the loop after the stop command
                }
            }
        });
        commandListener.start(); // Start the command listener thread

        // Wait for all vendor threads to finish
        for (Thread vendor : vendorThreads) {
            vendor.join();
        }

        // Wait for all customer threads to finish
        for (Thread customer : customerThreads) {
            customer.join();
        }

        // Wait for the command listener thread to finish
        commandListener.join();

        // Print message after all threads have terminated
        System.out.println("All threads terminated. Exiting application.");
    }
}


