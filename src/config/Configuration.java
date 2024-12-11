package config;

public class Configuration {
    // Total number of tickets in the system
    private int totalTickets;

    // Rate at which tickets are released (in seconds)
    private int releaseRate;

    // Rate at which customers retrieve tickets (in seconds)
    private int retrievalRate;

    // Maximum capacity of the ticket pool
    private int maximumTicketCapacity;

    // Number of vendors in the system
    private int numberOfVendors;

    // Number of customers in the system
    private int numberOfCustomers;

    // Constructor to initialize all fields
    public Configuration(int totalTickets, int releaseRate, int retrievalRate,
                         int maximumTicketCapacity, int numberOfVendors, int numberOfCustomers) {
        this.totalTickets = totalTickets;
        this.releaseRate = releaseRate;
        this.retrievalRate = retrievalRate;
        this.maximumTicketCapacity = maximumTicketCapacity;
        this.numberOfVendors = numberOfVendors;
        this.numberOfCustomers = numberOfCustomers;
    }

    // Default constructor for deserialization
    public Configuration() {}

    // Converts the configuration object to a string representation
    @Override
    public String toString() {
        return "Configuration {" +
                "totalTickets=" + totalTickets +
                ", releaseRate=" + releaseRate +
                ", retrievalRate=" + retrievalRate +
                ", maximumTicketCapacity=" + maximumTicketCapacity +
                ", numberOfVendors=" + numberOfVendors +
                ", numberOfCustomers=" + numberOfCustomers +
                '}';
    }

    // Getters and setters for all fields
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
}
