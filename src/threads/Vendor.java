package threads;

import core.AbstractTicketHandler;
import core.TicketPool;
import logging.Logger;
import util.Ticket;

public class Vendor extends AbstractTicketHandler implements Runnable {
    // Rate at which the vendor releases tickets (in seconds)
    private final int releaseRate;

    // Total number of tickets the vendor will release
    private final int totalTickets;

    // Constructor to initialize the vendor with ticket pool, release rate, and total tickets
    public Vendor(TicketPool ticketPool, int releaseRate, int totalTickets) {
        super(ticketPool);  // Call the parent class constructor to set ticket pool
        this.releaseRate = releaseRate;
        this.totalTickets = totalTickets;
    }

    // Implements the ticketHandler method from AbstractTicketHandler, which runs the vendor thread
    @Override
    protected void ticketHandler() {
        run();
    }

    // Runs the vendor thread that releases tickets at a specified rate
    @Override
    public void run() {
        while (!ticketPool.shouldStop() && ticketPool.getVendorCount() < totalTickets) {
            // Create a new ticket with a unique ID
            Ticket ticket = new Ticket(Thread.currentThread().getName(), "ID" + System.nanoTime());
            ticketPool.addTicket(ticket); // Add the ticket to the ticket pool

            try {
                // Sleep for the release rate time before releasing the next ticket
                Thread.sleep(releaseRate * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                break; // Exit the loop if the thread is interrupted
            }
        }
        // Log when the vendor thread stops
        Logger.log(Thread.currentThread().getName() + " stopped.");
    }
}

