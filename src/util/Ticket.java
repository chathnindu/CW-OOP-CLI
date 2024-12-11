package util;

// Represents a ticket with a unique ID and a vendor ID.
public class Ticket {
    private String ticketId; // The ticket's unique identifier.
    private String vendorId; // The vendor's identifier for this ticket.

    // Initializes a Ticket with the given vendor ID and ticket ID.
    public Ticket(String vendorId,String ticketId) {
        this.vendorId = vendorId;
        this.ticketId = ticketId;
    }

    // Returns the unique ID of the ticket.
    public String getTicketId() {
        return ticketId;
    }

    // Returns the ID of the vendor who issued the ticket.
    public String getVendorId() {
        return vendorId;
    }
}
