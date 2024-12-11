package core;

public abstract class AbstractTicketHandler {
    // The ticket pool that this handler operates on
    protected TicketPool ticketPool;

    // Abstract method for handling ticket operations (to be implemented by subclasses)
    protected abstract void ticketHandler();

    // Constructor to initialize the ticket pool
    public AbstractTicketHandler(TicketPool ticketPool) {
        this.ticketPool = ticketPool; // Assign the provided ticket pool
    }
}
