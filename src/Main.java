import config.Configuration;
import core.TicketPool;
import logging.Logger;
import threads.Customer;
import threads.Vendor;
import ui.CommandLineInterface;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Configuration config = CommandLineInterface.initializeFromCLI();
        TicketPool ticketPool = new TicketPool(config.getMaximumTicketCapacity());

        Logger.clearLogFile(); // Clear logs
        Logger.log("System initialized with configuration: " + config);

        // Vendor threads
        Thread[] vendorThreads = new Thread[config.getNumberOfVendors()];
        for (int i = 0; i < config.getNumberOfVendors(); i++) {
            vendorThreads[i] = new Thread(new Vendor(ticketPool, config.getReleaseRate(), config.getTotalTickets()));
            vendorThreads[i].setName("Vendor-" + i);
            vendorThreads[i].start();
        }

        // VIP Customer threads
        int vipCustomers = config.getNumberOfVipCustomers();
        Thread[] vipCustomerThreads = new Thread[vipCustomers];
        for (int i = 0; i < vipCustomers; i++) {
            vipCustomerThreads[i] = new Thread(new Customer(ticketPool, config.getRetrievalRate(), config.getTotalTickets(), true));
            vipCustomerThreads[i].setName("VIP Customer-" + i);
            vipCustomerThreads[i].setPriority(Thread.MAX_PRIORITY);
            vipCustomerThreads[i].start();
        }

        // Regular Customer threads
        int regularCustomers = config.getNumberOfCustomers() - vipCustomers;
        Thread[] regularCustomerThreads = new Thread[regularCustomers];
        for (int i = 0; i < regularCustomers; i++) {
            regularCustomerThreads[i] = new Thread(new Customer(ticketPool, config.getRetrievalRate(), config.getTotalTickets(), false));
              regularCustomerThreads[i].setName("Customer-" + i);
            regularCustomerThreads[i].start();
        }

        // Command Listener
        Thread commandListener = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String command = scanner.nextLine().trim();
                if ("stop".equalsIgnoreCase(command)) {
                    ticketPool.setStopFlag(true);
                    Logger.log("Stop command received. Shutting down...");
                    for (Thread thread : vendorThreads) thread.interrupt();
                    for (Thread thread : vipCustomerThreads) thread.interrupt();
                    for (Thread thread : regularCustomerThreads) thread.interrupt();
                    break;
                }
            }
        });
        commandListener.start();

        for (Thread thread : vendorThreads) thread.join();
        for (Thread thread : vipCustomerThreads) thread.join();
        for (Thread thread : regularCustomerThreads) thread.join();
        commandListener.join();

        Logger.log("System terminated.");
    }
}




