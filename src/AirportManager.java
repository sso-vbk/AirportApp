import java.util.*;
import java.util.stream.Collectors;

public class AirportManager {
    private final Map<String, Passenger> passengers;
    private final Map<String, Flight> flights;

    public AirportManager() {
        this.passengers = new HashMap<>();
        this.flights = new HashMap<>();
    }

    // Passenger Management
    public Passenger addPassenger(String name, int age, String address, double baggageWeight)
            throws IllegalArgumentException {
        Passenger passenger = new Passenger(name, age, address, baggageWeight);
        passengers.put(passenger.getPassengerId(), passenger);
        return passenger;
    }

    public Passenger findPassengerById(String passengerId) {
        return passengers.get(passengerId);
    }

    public List<Passenger> findPassengersByName(String name) {
        return passengers.values().stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Passenger> getAllPassengers() {
        return new ArrayList<>(passengers.values());
    }

    public boolean removePassenger(String passengerId) {
        // First remove from all flights
        for (Flight flight : flights.values()) {
            flight.removePassenger(passengerId);
        }
        return passengers.remove(passengerId) != null;
    }

    // Flight Management
    public Flight addFlight(String flightCode, String destination, String origin,
                            int economySeats, int businessSeats) {
        if (flights.containsKey(flightCode.toUpperCase())) {
            throw new IllegalArgumentException("Flight with code " + flightCode + " already exists");
        }
        Flight flight = new Flight(flightCode, destination, origin, economySeats, businessSeats);
        flights.put(flight.getFlightCode(), flight);
        return flight;
    }

    public Flight findFlightByCode(String flightCode) {
        return flights.get(flightCode.toUpperCase());
    }

    public List<Flight> findFlightsByDestination(String destination) {
        return flights.values().stream()
                .filter(f -> f.getDestination().toLowerCase().contains(destination.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Flight> getAllFlights() {
        return new ArrayList<>(flights.values());
    }

    public boolean removeFlight(String flightCode) {
        return flights.remove(flightCode.toUpperCase()) != null;
    }

    // Boarding Operations
    public boolean boardPassenger(String passengerId, String flightCode,
                                  Passenger.TicketClass ticketClass) {
        Passenger passenger = findPassengerById(passengerId);
        if (passenger == null) {
            throw new IllegalArgumentException("Passenger not found: " + passengerId);
        }

        Flight flight = findFlightByCode(flightCode);
        if (flight == null) {
            throw new IllegalArgumentException("Flight not found: " + flightCode);
        }

        return flight.boardPassenger(passenger, ticketClass);
    }

    public boolean boardPassengerByName(String passengerName, String flightCode,
                                        Passenger.TicketClass ticketClass) {
        List<Passenger> matchingPassengers = findPassengersByName(passengerName);
        if (matchingPassengers.isEmpty()) {
            throw new IllegalArgumentException("No passenger found with name: " + passengerName);
        }
        if (matchingPassengers.size() > 1) {
            throw new IllegalArgumentException("Multiple passengers found with name: " + passengerName +
                    ". Please use passenger ID instead.");
        }

        return boardPassenger(matchingPassengers.get(0).getPassengerId(), flightCode, ticketClass);
    }

    // Statistics and Reports
    public void printFlightManifest(String flightCode) {
        Flight flight = findFlightByCode(flightCode);
        if (flight == null) {
            System.out.println("Flight not found: " + flightCode);
            return;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("FLIGHT MANIFEST");
        System.out.println("=".repeat(60));
        System.out.println(flight.getFlightInfo());
        System.out.println("-".repeat(60));

        List<Passenger> passengerList = flight.getPassengerList();
        if (passengerList.isEmpty()) {
            System.out.println("No passengers boarded yet.");
        } else {
            System.out.println("PASSENGER LIST:");

            // Separate by class
            List<Passenger> businessPassengers = passengerList.stream()
                    .filter(p -> p.getTicketClass() == Passenger.TicketClass.BUSINESS)
                    .collect(Collectors.toList());

            List<Passenger> economyPassengers = passengerList.stream()
                    .filter(p -> p.getTicketClass() == Passenger.TicketClass.ECONOMY)
                    .collect(Collectors.toList());

            if (!businessPassengers.isEmpty()) {
                System.out.println("\nBusiness Class:");
                businessPassengers.forEach(p -> System.out.println("  • " + p));
            }

            if (!economyPassengers.isEmpty()) {
                System.out.println("\nEconomy Class:");
                economyPassengers.forEach(p -> System.out.println("  • " + p));
            }
        }
        System.out.println("=".repeat(60));
    }

    public void printAllFlights() {
        if (flights.isEmpty()) {
            System.out.println("No flights scheduled.");
            return;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("ALL SCHEDULED FLIGHTS");
        System.out.println("=".repeat(60));

        flights.values().forEach(flight -> {
            System.out.println(flight.getFlightInfo());
            System.out.println("Passengers: " + flight.getTotalPassengers());
            System.out.println("-".repeat(60));
        });
    }

    public void printAirportStatistics() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("AIRPORT STATISTICS");
        System.out.println("=".repeat(60));
        System.out.println("Total Registered Passengers: " + passengers.size());
        System.out.println("Total Scheduled Flights: " + flights.size());

        if (!flights.isEmpty()) {
            int totalPassengersOnFlights = flights.values().stream()
                    .mapToInt(Flight::getTotalPassengers)
                    .sum();
            double avgOccupancy = flights.values().stream()
                    .mapToDouble(Flight::getOccupancyRate)
                    .average()
                    .orElse(0);

            System.out.println("Total Passengers Boarded: " + totalPassengersOnFlights);
            System.out.printf("Average Flight Occupancy: %.1f%%\n", avgOccupancy);
        }
        System.out.println("=".repeat(60));
    }
}