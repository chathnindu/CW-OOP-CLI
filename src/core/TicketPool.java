package core;

import logging.Logger;
import util.Ticket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketPool implements TicketOperation {
    // A synchronized list to hold the tickets in the pool
    private final List<Ticket> ticketPool = Collections.synchronizedList(new ArrayList<>());

    // Maximum capacity of the ticket pool
    private final int maximumCapacity;

    // Count of sold tickets
    private int soldTicketCount = 0;

    // Count of tickets added by vendors
    private int vendorCount = 0;

    // Flag to indicate if the system should stop
    private volatile boolean stopFlag = false;

    // Constructor to initialize the maximum capacity of the ticket pool
    public TicketPool(int maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    // Returns the count of sold tickets
    public int getSoldTicketCount() {
        return soldTicketCount;
    }

    // Returns the count of vendors who have added tickets
    public int getVendorCount() {
        return vendorCount;
    }

    // Adds a ticket to the pool if there is space
    @Override
    public synchronized void addTicket(Ticket ticket) {
        if (ticketPool.size() >= maximumCapacity) {
            try {
                Logger.log("Ticket pool is full");
                wait();  // Wait until there is space in the pool
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        ticketPool.add(ticket);  // Add ticket to the pool
        vendorCount++;  // Increment vendor count
        Logger.log("Ticket added by " + ticket.getVendorId() + "; Ticket ID: " + ticket.getTicketId());
        notify();  // Notify any waiting threads
    }

    // Removes a ticket from the pool when a customer buys it
    @Override
    public synchronized void removeTicket() {
        Ticket ticket = null;
        if (!ticketPool.isEmpty()) {
            ticket = ticketPool.remove(0);  // Remove the first ticket from the pool
            soldTicketCount++;  // Increment the sold ticket count
            Logger.log("Ticket purchased by " + Thread.currentThread().getName() +
                    "; Ticket ID: " + ticket.getTicketId() + "; Vendor ID: " + ticket.getVendorId());
            notifyAll();  // Notify all waiting threads
        } else {
            Logger.log("Ticket pool is empty");
            try {
                wait();  // Wait until a ticket is added to the pool
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Checks if the stop flag is set to true
    public boolean shouldStop() {
        return stopFlag;
    }

    // Sets the stop flag to true and notifies all waiting threads
    public synchronized void setStopFlag(boolean stopFlag) {
        this.stopFlag = stopFlag;
        notifyAll();  // Wake up all waiting threads
    }
}

