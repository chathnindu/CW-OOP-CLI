package core;

import util.Ticket;

public interface TicketOperation {
    // Adds a ticket to the ticket pool (may throw InterruptedException if interrupted)
    void addTicket(Ticket ticket) throws InterruptedException;

    // Removes a ticket from the ticket pool (may throw InterruptedException if interrupted)
    void removeTicket() throws InterruptedException;
}

