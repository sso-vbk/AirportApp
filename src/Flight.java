import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Flight {
    private final String flightCode;
    private String destination;
    private String origin;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int totalEconomySeats;
    private int totalBusinessSeats;
    private int availableEconomySeats;
    private int availableBusinessSeats;
    private final Map<String, Passenger> passengers;
    private FlightStatus status;

    public enum FlightStatus {
        SCHEDULED("Scheduled"),
        BOARDING("Boarding"),
        DEPARTED("Departed"),
        ARRIVED("Arrived"),
        CANCELLED("Cancelled"),
        DELAYED("Delayed");

        private final String displayName;

        FlightStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public Flight(String flightCode, String destination, String origin,
                  int economySeats, int businessSeats) {
        this.flightCode = flightCode.toUpperCase();
        this.destination = destination;
        this.origin = origin;
        this.totalEconomySeats = economySeats;
        this.totalBusinessSeats = businessSeats;
        this.availableEconomySeats = economySeats;
        this.availableBusinessSeats = businessSeats;
        this.passengers = new HashMap<>();
        this.status = FlightStatus.SCHEDULED;
        this.departureTime = LocalDateTime.now().plusHours(2); // Default 2 hours from now
        this.arrivalTime = departureTime.plusHours(3); // Default 3-hour flight
    }

    public boolean boardPassenger(Passenger passenger, Passenger.TicketClass ticketClass) {
        if (passengers.containsKey(passenger.getPassengerId())) {
            throw new IllegalStateException("Passenger already boarded on this flight");
        }

        if (status != FlightStatus.SCHEDULED && status != FlightStatus.BOARDING) {
            throw new IllegalStateException("Cannot board passengers. Flight status: " + status.getDisplayName());
        }

        boolean seatAvailable = false;

        if (ticketClass == Passenger.TicketClass.ECONOMY && availableEconomySeats > 0) {
            availableEconomySeats--;
            seatAvailable = true;
        } else if (ticketClass == Passenger.TicketClass.BUSINESS && availableBusinessSeats > 0) {
            availableBusinessSeats--;
            seatAvailable = true;
        }

        if (seatAvailable) {
            passenger.setTicketClass(ticketClass);
            passengers.put(passenger.getPassengerId(), passenger);
            return true;
        }

        return false;
    }

    public boolean removePassenger(String passengerId) {
        Passenger removed = passengers.remove(passengerId);
        if (removed != null) {
            if (removed.getTicketClass() == Passenger.TicketClass.ECONOMY) {
                availableEconomySeats++;
            } else {
                availableBusinessSeats++;
            }
            return true;
        }
        return false;
    }

    public List<Passenger> getPassengerList() {
        return new ArrayList<>(passengers.values());
    }

    public int getTotalPassengers() {
        return passengers.size();
    }

    public double getOccupancyRate() {
        int totalSeats = totalEconomySeats + totalBusinessSeats;
        return totalSeats > 0 ? (double) passengers.size() / totalSeats * 100 : 0;
    }

    // Getters and Setters
    public String getFlightCode() {
        return flightCode;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getAvailableEconomySeats() {
        return availableEconomySeats;
    }

    public int getAvailableBusinessSeats() {
        return availableBusinessSeats;
    }

    public int getTotalEconomySeats() {
        return totalEconomySeats;
    }

    public int getTotalBusinessSeats() {
        return totalBusinessSeats;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    public String getFlightInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format(
                "Flight %s: %s → %s\n" +
                        "Status: %s\n" +
                        "Departure: %s | Arrival: %s\n" +
                        "Economy: %d/%d available | Business: %d/%d available\n" +
                        "Total Passengers: %d (%.1f%% occupancy)",
                flightCode, origin, destination,
                status.getDisplayName(),
                departureTime.format(formatter), arrivalTime.format(formatter),
                availableEconomySeats, totalEconomySeats,
                availableBusinessSeats, totalBusinessSeats,
                getTotalPassengers(), getOccupancyRate()
        );
    }

    @Override
    public String toString() {
        return getFlightInfo();
    }
}