package config;

public class Configuration {
    private int totalTickets;             // Total number of tickets
    private int releaseRate;              // Ticket release rate in seconds
    private int retrievalRate;            // Customer ticket retrieval rate in seconds
    private int maximumTicketCapacity;    // Maximum ticket pool capacity
    private int numberOfVendors;          // Number of vendors
    private int numberOfCustomers;        // Number of customers
    private int numberOfVipCustomers;     // Number of VIP customers

    // Constructor
    public Configuration(int totalTickets, int releaseRate, int retrievalRate,
                         int maximumTicketCapacity, int numberOfVendors, int numberOfCustomers, int numberOfVipCustomers) {
        this.totalTickets = totalTickets;
        this.releaseRate = releaseRate;
        this.retrievalRate = retrievalRate;
        this.maximumTicketCapacity = maximumTicketCapacity;
        this.numberOfVendors = numberOfVendors;
        this.numberOfCustomers = numberOfCustomers;
        this.numberOfVipCustomers = numberOfVipCustomers;
    }

    // Default constructor
    public Configuration() {}

    // String representation of configuration
    @Override
    public String toString() {
        return "Configuration {" +
                "totalTickets=" + totalTickets +
                ", releaseRate=" + releaseRate +
                ", retrievalRate=" + retrievalRate +
                ", maximumTicketCapacity=" + maximumTicketCapacity +
                ", numberOfVendors=" + numberOfVendors +
                ", numberOfCustomers=" + numberOfCustomers +
                ", numberOfVipCustomers=" + numberOfVipCustomers +
                '}';
    }

    // Getters and setters
    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getReleaseRate() {
        return releaseRate;
    }

    public void setReleaseRate(int releaseRate) {
        this.releaseRate = releaseRate;
    }

    public int getRetrievalRate() {
        return retrievalRate;
    }

    public void setRetrievalRate(int retrievalRate) {
        this.retrievalRate = retrievalRate;
    }

    public int getMaximumTicketCapacity() {
        return maximumTicketCapacity;
    }

    public void setMaximumTicketCapacity(int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
    }

    public int getNumberOfVendors() {
        return numberOfVendors;
    }

    public void setNumberOfVendors(int numberOfVendors) {
        this.numberOfVendors = numberOfVendors;
    }

    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public void setNumberOfCustomers(int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }

    public int getNumberOfVipCustomers() {
        return numberOfVipCustomers;
    }

    public void setNumberOfVipCustomers(int numberOfVipCustomers) {
        this.numberOfVipCustomers = numberOfVipCustomers;
    }
}

