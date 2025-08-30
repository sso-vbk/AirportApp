import java.util.UUID;

public class Passenger extends Person {
    private final String passengerId;
    private double baggageWeight;
    private static final double MAX_BAGGAGE_WEIGHT = 20.0;
    private TicketClass ticketClass;

    public enum TicketClass {
        ECONOMY("Economy", 1.0),
        BUSINESS("Business", 1.5);

        private final String name;
        private final double priceMultiplier;

        TicketClass(String name, double priceMultiplier) {
            this.name = name;
            this.priceMultiplier = priceMultiplier;
        }

        public String getName() {
            return name;
        }

        public double getPriceMultiplier() {
            return priceMultiplier;
        }
    }

    public Passenger(String name, int age, String address, double baggageWeight) {
        super(name, age, address);
        this.passengerId = generatePassengerId();
        setBaggageWeight(baggageWeight);
        this.ticketClass = TicketClass.ECONOMY; // Default to economy
    }

    private String generatePassengerId() {
        return "P" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public String getPassengerId() {
        return passengerId;
    }

    public double getBaggageWeight() {
        return baggageWeight;
    }

    public void setBaggageWeight(double baggageWeight) {
        if (baggageWeight < 0) {
            throw new IllegalArgumentException("Baggage weight cannot be negative");
        }
        if (baggageWeight > MAX_BAGGAGE_WEIGHT) {
            throw new IllegalArgumentException(
                    String.format("Baggage weight exceeds maximum allowed weight of %.1f kg", MAX_BAGGAGE_WEIGHT)
            );
        }
        this.baggageWeight = baggageWeight;
    }

    public TicketClass getTicketClass() {
        return ticketClass;
    }

    public void setTicketClass(TicketClass ticketClass) {
        this.ticketClass = ticketClass;
    }

    public boolean hasExcessBaggage() {
        return baggageWeight > MAX_BAGGAGE_WEIGHT;
    }

    @Override
    public String toString() {
        return String.format("Passenger [ID: %s, Name: %s, Age: %d, Baggage: %.1f kg, Class: %s]",
                passengerId, getName(), getAge(), baggageWeight, ticketClass.getName());
    }
}