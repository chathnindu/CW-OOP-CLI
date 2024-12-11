package threads;

import core.AbstractTicketHandler;
import core.TicketPool;
import logging.Logger;

public class Customer extends AbstractTicketHandler implements Runnable {
    // Rate at which the customer retrieves tickets (in seconds)
    private int retrievalRate;

    // Total number of tickets the customer will retrieve
    private int totalTickets;

    // Constructor to initialize the customer with ticket pool, retrieval rate, and total tickets
    public Customer(TicketPool ticketPool, int retrievalRate, int totalTickets) {
        super(ticketPool);  // Call the parent class constructor to set ticket pool
        this.retrievalRate = retrievalRate;
        this.totalTickets = totalTickets;
    }

    // Implements the ticketHandler method from AbstractTicketHandler, which runs the customer thread
    @Override
    protected void ticketHandler() {
        run();
    }

    // Runs the customer thread that retrieves tickets from the pool at a specified rate
    @Override
    public synchronized void run() {
        while (!ticketPool.shouldStop() && ticketPool.getSoldTicketCount() < totalTickets) {
            ticketPool.removeTicket();  // Retrieve a ticket from the pool

            try {
                // Sleep for the retrieval rate time before retrieving the next ticket
                Thread.sleep(retrievalRate * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                break; // Exit the loop if the thread is interrupted
            }
        }
        // Log when the customer thread stops
        Logger.log(Thread.currentThread().getName() + " stopped.");
    }
}

