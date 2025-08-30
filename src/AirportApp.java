import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AirportApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AirportManager airportManager = new AirportManager();
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        initializeSampleData();

        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   AIRPORT MANAGEMENT SYSTEM            ║");
        System.out.println("╚════════════════════════════════════════╝");

        while (true) {
            try {
                showMainMenu();
                int choice = getIntInput("Enter your choice: ");

                switch (choice) {
                    case 1 -> passengerMenu();
                    case 2 -> flightMenu();
                    case 3 -> boardingMenu();
                    case 4 -> reportsMenu();
                    case 5 -> {
                        System.out.println("\nThank you for using Airport Management System!");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n┌─── MAIN MENU ───┐");
        System.out.println("│ 1. Passenger Management");
        System.out.println("│ 2. Flight Management");
        System.out.println("│ 3. Boarding Operations");
        System.out.println("│ 4. Reports & Statistics");
        System.out.println("│ 5. Exit");
        System.out.println("└─────────────────┘");
    }

    private static void passengerMenu() {
        while (true) {
            System.out.println("\n┌─── PASSENGER MANAGEMENT ───┐");
            System.out.println("│ 1. Add New Passenger");
            System.out.println("│ 2. View All Passengers");
            System.out.println("│ 3. Search Passenger");
            System.out.println("│ 4. Remove Passenger");
            System.out.println("│ 5. Back to Main Menu");
            System.out.println("└────────────────────────────┘");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1 -> addPassenger();
                case 2 -> viewAllPassengers();
                case 3 -> searchPassenger();
                case 4 -> removePassenger();
                case 5 -> { return; }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void flightMenu() {
        while (true) {
            System.out.println("\n┌─── FLIGHT MANAGEMENT ───┐");
            System.out.println("│ 1. Add New Flight");
            System.out.println("│ 2. View All Flights");
            System.out.println("│ 3. Search Flight");
            System.out.println("│ 4. Update Flight Status");
            System.out.println("│ 5. Remove Flight");
            System.out.println("│ 6. Back to Main Menu");
            System.out.println("└─────────────────────────┘");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1 -> addFlight();
                case 2 -> airportManager.printAllFlights();
                case 3 -> searchFlight();
                case 4 -> updateFlightStatus();
                case 5 -> removeFlight();
                case 6 -> { return; }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void boardingMenu() {
        while (true) {
            System.out.println("\n┌─── BOARDING OPERATIONS ───┐");
            System.out.println("│ 1. Board Passenger");
            System.out.println("│ 2. View Flight Manifest");
            System.out.println("│ 3. Back to Main Menu");
            System.out.println("└───────────────────────────┘");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1 -> boardPassenger();
                case 2 -> viewFlightManifest();
                case 3 -> { return; }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void reportsMenu() {
        while (true) {
            System.out.println("\n┌─── REPORTS & STATISTICS ───┐");
            System.out.println("│ 1. Airport Statistics");
            System.out.println("│ 2. All Flight Manifests");
            System.out.println("│ 3. Back to Main Menu");
            System.out.println("└────────────────────────────┘");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1 -> airportManager.printAirportStatistics();
                case 2 -> printAllManifests();
                case 3 -> { return; }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Passenger Operations
    private static void addPassenger() {
        System.out.println("\n--- Add New Passenger ---");
        scanner.nextLine(); // Clear buffer

        System.out.print("Enter passenger name: ");
        String name = scanner.nextLine();

        int age = getIntInput("Enter age: ");
        scanner.nextLine(); // Clear buffer

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        double baggageWeight = getDoubleInput("Enter baggage weight (kg): ");

        try {
            Passenger passenger = airportManager.addPassenger(name, age, address, baggageWeight);
            System.out.println("✓ Passenger added successfully!");
            System.out.println("Passenger ID: " + passenger.getPassengerId());
            System.out.println(passenger);
        } catch (IllegalArgumentException e) {
            System.err.println("✗ Failed to add passenger: " + e.getMessage());
        }
    }

    private static void viewAllPassengers() {
        List<Passenger> passengers = airportManager.getAllPassengers();
        if (passengers.isEmpty()) {
            System.out.println("No passengers registered.");
            return;
        }

        System.out.println("\n--- All Passengers ---");
        passengers.forEach(System.out::println);
    }

    private static void searchPassenger() {
        scanner.nextLine(); // Clear buffer
        System.out.print("Enter passenger name to search: ");
        String name = scanner.nextLine();

        List<Passenger> found = airportManager.findPassengersByName(name);
        if (found.isEmpty()) {
            System.out.println("No passengers found with name: " + name);
        } else {
            System.out.println("Found " + found.size() + " passenger(s):");
            found.forEach(System.out::println);
        }
    }

    private static void removePassenger() {
        scanner.nextLine(); // Clear buffer
        System.out.print("Enter passenger ID to remove: ");
        String passengerId = scanner.nextLine();

        if (airportManager.removePassenger(passengerId)) {
            System.out.println("✓ Passenger removed successfully!");
        } else {
            System.out.println("✗ Passenger not found!");
        }
    }

    // Flight Operations
    private static void addFlight() {
        System.out.println("\n--- Add New Flight ---");
        scanner.nextLine(); // Clear buffer

        System.out.print("Enter flight code (e.g., AA123): ");
        String flightCode = scanner.nextLine();

        System.out.print("Enter origin city: ");
        String origin = scanner.nextLine();

        System.out.print("Enter destination city: ");
        String destination = scanner.nextLine();

        int economySeats = getIntInput("Enter number of economy seats: ");
        int businessSeats = getIntInput("Enter number of business seats: ");

        try {
            Flight flight = airportManager.addFlight(flightCode, destination, origin,
                    economySeats, businessSeats);
            System.out.println("✓ Flight added successfully!");
            System.out.println(flight);
        } catch (IllegalArgumentException e) {
            System.err.println("✗ Failed to add flight: " + e.getMessage());
        }
    }

    private static void searchFlight() {
        scanner.nextLine(); // Clear buffer
        System.out.print("Enter destination to search: ");
        String destination = scanner.nextLine();

        List<Flight> found = airportManager.findFlightsByDestination(destination);
        if (found.isEmpty()) {
            System.out.println("No flights found to: " + destination);
        } else {
            System.out.println("Found " + found.size() + " flight(s):");
            found.forEach(flight -> {
                System.out.println(flight);
                System.out.println();
            });
        }
    }

    private static void updateFlightStatus() {
        scanner.nextLine(); // Clear buffer
        System.out.print("Enter flight code: ");
        String flightCode = scanner.nextLine();

        Flight flight = airportManager.findFlightByCode(flightCode);
        if (flight == null) {
            System.out.println("Flight not found!");
            return;
        }

        System.out.println("Current status: " + flight.getStatus().getDisplayName());
        System.out.println("Select new status:");
        Flight.FlightStatus[] statuses = Flight.FlightStatus.values();
        for (int i = 0; i < statuses.length; i++) {
            System.out.println((i + 1) + ". " + statuses[i].getDisplayName());
        }

        int choice = getIntInput("Enter choice: ");
        if (choice > 0 && choice <= statuses.length) {
            flight.setStatus(statuses[choice - 1]);
            System.out.println("✓ Flight status updated successfully!");
        } else {
            System.out.println("Invalid choice!");
        }
    }

    private static void removeFlight() {
        scanner.nextLine(); // Clear buffer
        System.out.print("Enter flight code to remove: ");
        String flightCode = scanner.nextLine();

        if (airportManager.removeFlight(flightCode)) {
            System.out.println("✓ Flight removed successfully!");
        } else {
            System.out.println("✗ Flight not found!");
        }
    }

    // Boarding Operations
    private static void boardPassenger() {
        System.out.println("\n--- Board Passenger ---");
        scanner.nextLine(); // Clear buffer

        System.out.print("Enter passenger name or ID: ");
        String passengerInput = scanner.nextLine();

        System.out.print("Enter flight code: ");
        String flightCode = scanner.nextLine();

        System.out.println("Select ticket class:");
        System.out.println("1. Economy");
        System.out.println("2. Business");

        int classChoice = getIntInput("Enter choice: ");
        Passenger.TicketClass ticketClass = (classChoice == 2) ?
                Passenger.TicketClass.BUSINESS : Passenger.TicketClass.ECONOMY;

        try {
            boolean success;
            // Check if input is a passenger ID (starts with P) or name
            if (passengerInput.startsWith("P")) {
                success = airportManager.boardPassenger(passengerInput, flightCode, ticketClass);
            } else {
                success = airportManager.boardPassengerByName(passengerInput, flightCode, ticketClass);
            }

            if (success) {
                System.out.println("✓ Passenger boarded successfully!");
            } else {
                System.out.println("✗ No seats available in " + ticketClass.getName() + " class!");
            }
        } catch (Exception e) {
            System.err.println("✗ Boarding failed: " + e.getMessage());
        }
    }

    private static void viewFlightManifest() {
        scanner.nextLine(); // Clear buffer
        System.out.print("Enter flight code: ");
        String flightCode = scanner.nextLine();

        airportManager.printFlightManifest(flightCode);
    }

    private static void printAllManifests() {
        List<Flight> flights = airportManager.getAllFlights();
        if (flights.isEmpty()) {
            System.out.println("No flights scheduled.");
            return;
        }

        for (Flight flight : flights) {
            airportManager.printFlightManifest(flight.getFlightCode());
            System.out.println();
        }
    }

    // Utility Methods
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Invalid input! Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.err.println("Invalid input! Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    // Initialize sample data for testing
    private static void initializeSampleData() {
        try {
            // Add sample passengers
            airportManager.addPassenger("John Smith", 35, "123 Main St, New York", 15.5);
            airportManager.addPassenger("Sarah Johnson", 28, "456 Oak Ave, Los Angeles", 18.0);
            airportManager.addPassenger("Michael Brown", 42, "789 Pine Rd, Chicago", 12.0);
            airportManager.addPassenger("Emily Davis", 31, "321 Elm St, Houston", 19.5);
            airportManager.addPassenger("Robert Wilson", 55, "654 Maple Dr, Phoenix", 14.0);

            // Add sample flights
            Flight flight1 = airportManager.addFlight("AA101", "Los Angeles", "New York", 150, 30);
            Flight flight2 = airportManager.addFlight("UA202", "Chicago", "San Francisco", 120, 25);
            Flight flight3 = airportManager.addFlight("DL303", "Miami", "Boston", 100, 20);

            // Set departure times
            flight1.setDepartureTime(LocalDateTime.now().plusHours(3));
            flight1.setArrivalTime(flight1.getDepartureTime().plusHours(5));

            flight2.setDepartureTime(LocalDateTime.now().plusHours(5));
            flight2.setArrivalTime(flight2.getDepartureTime().plusHours(4));

            flight3.setDepartureTime(LocalDateTime.now().plusDays(1));
            flight3.setArrivalTime(flight3.getDepartureTime().plusHours(3));

            System.out.println("✓ Sample data loaded successfully!");

        } catch (Exception e) {
            System.err.println("Failed to initialize sample data: " + e.getMessage());
        }
    }
}