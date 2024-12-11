"# CW-OOP-CLI" 
# Ticketing System

## Overview
This Ticketing System is a multithreaded application designed to manage ticket sales through vendors and customers. Vendors add tickets to a shared pool, while customers retrieve tickets from the pool. The system ensures synchronization and handles edge cases such as empty or full ticket pools. Logging is implemented to record operations, and a `stop` command allows graceful shutdown.

## Features
- Multithreaded support for vendors and customers.
- Configurable ticket pool size, release rates, and retrieval rates.
- Logging of operations including ticket additions, retrievals, and errors.
- Graceful shutdown using the `stop` command.
- Input validation for configuration values.

## Architecture

### Classes
1. **`Main`**:
    - Entry point of the application.
    - Manages thread creation and system shutdown.

2. **`Configuration`**:
    - Holds system configuration details (e.g., total tickets, release rates).
    - Provides getter and setter methods.

3. **`CommandLineInterface`**:
    - Handles user input for system configuration.
    - Supports loading and saving configurations in JSON format.

4. **`TicketPool`**:
    - Shared resource for tickets between vendors and customers.
    - Manages ticket addition, retrieval, and synchronization.

5. **`Vendor`**:
    - Vendor threads add tickets to the `TicketPool` at a configurable rate.

6. **`Customer`**:
    - Customer threads retrieve tickets from the `TicketPool` at a configurable rate.

7. **`Logger`**:
    - Handles logging of system operations to a file with timestamps.
    - Supports clearing logs at the start of a session.

## Setup Instructions

### Prerequisites
- Java Development Kit (JDK) version 8 or higher.
- IDE (e.g., IntelliJ IDEA, Eclipse) or command-line tools.

### Build and Run
1. Clone or download the project.
2. Open the project in your preferred IDE or navigate to the project directory in the terminal.
3. Compile the project using the following command:
   ```
   javac -d bin src/**/*.java
   ```
4. Run the application:
   ```
   java -cp bin Main
   ```

## Usage Instructions

### Configuring the System
1. Launch the application.
2. Enter the required configuration details when prompted:
    - Total number of tickets.
    - Ticket release rate (seconds).
    - Ticket retrieval rate (seconds).
    - Maximum ticket pool capacity.
    - Number of vendors.
    - Number of customers.
3. The system will save the configuration for future use.

### Commands
- **`stop`**: Stops all threads and gracefully shuts down the system.

### Logs
- Logs are saved in `resource/log.txt`.
- Log entries include timestamps and details about ticket operations (e.g., additions, retrievals).

### Example Workflow
1. Start the system and provide configuration values.
2. Observe the ticket addition and retrieval operations in real-time.
3. Type `stop` to terminate the application.
4. Review `log.txt` for operation details.

## Testing
Refer to the test cases documented in the `Test Cases` section. These include:
- System initialization with valid and invalid inputs.
- Ticket addition and retrieval operations.
- Handling full and empty pools.
- Graceful shutdown and thread management.
- Performance under high concurrency.

## License
This project is a coursework submission and should not be redistributed or used for commercial purposes without prior permission. Contact the author for any inquiries.

