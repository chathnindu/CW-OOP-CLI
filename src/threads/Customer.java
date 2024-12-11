package threads;

import core.AbstractTicketHandler;
import core.TicketPool;
import logging.Logger;

public class Customer extends AbstractTicketHandler implements Runnable {
    private int retrievalRate; // Time delay between ticket retrievals
    private int totalTickets; // Total tickets to retrieve
    private boolean isVip;    // Flag to indicate if the customer is a VIP

    // Constructor
    public Customer(TicketPool ticketPool, int retrievalRate, int totalTickets, boolean isVip) {
        super(ticketPool);
        this.retrievalRate = retrievalRate;
        this.totalTickets = totalTickets;
        this.isVip = isVip;
    }

    @Override
    protected void ticketHandler() {
        run();
    }

    @Override
    public void run() {
        while (!ticketPool.shouldStop() && ticketPool.getSoldTicketCount() < totalTickets) {
            synchronized (ticketPool) {
                ticketPool.removeTicket(); // Purchased a ticket
            }
            Logger.log(Thread.currentThread().getName() + " purchased a ticket.");

            // Exit after one round if the customer is VIP
            if (isVip) {
                Logger.log(Thread.currentThread().getName() + " is a VIP and has stopped after one ticket.");
                break;
            }

            // Regular customers wait for the retrieval rate before the next round
            try {
                Thread.sleep(retrievalRate * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                break;
            }
        }
    }
}


