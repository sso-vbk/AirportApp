Airport Management System

📋 Overview
A comprehensive Java-based Airport Management System with improved architecture, error handling, and enhanced features.

1. Proper Object-Oriented Design
- Inheritance Hierarchy: `Person` base class with `Passenger` extending it
- Encapsulation: Private fields with proper getters/setters
- Separation of Concerns: Business logic separated into `AirportManager` service class

2. Enhanced Data Models

Person Class
- Base class for common person attributes
- Validation for age (must be positive)
- Proper toString() implementation

Passenger Class
- Extends Person class
- Auto-generated unique passenger IDs
- Ticket class support (Economy/Business)
- Baggage weight validation with maximum limit
- Custom exceptions for invalid data

Flight Class
- Comprehensive flight information (origin, destination, times)
- Separate tracking for total and available seats
- Flight status management (Scheduled, Boarding, Departed, etc.)
- Passenger manifest with class separation
- Occupancy rate calculation
- Proper boarding validation

3. Service Layer (AirportManager)
- Centralized management of passengers and flights
- Search capabilities (by name, ID, destination)
- Bulk operations support
- Statistics and reporting functions
- Error handling with meaningful messages

4. User Interface
- Organized menu structure with sub-menus
- Clear visual separation using box-drawing characters
- Better input validation and error messages
- Confirmation messages for operations
- Comprehensive reporting options

5. New Features
- Passenger IDs: Unique identifiers for each passenger
- Flight Status Tracking**: Monitor flight states
- Ticket Classes: Economy and Business class support
- Search Functions: Find passengers/flights by various criteria
- Statistics Dashboard: Airport-wide statistics
- Flight Manifests: Detailed passenger lists per flight
- Sample Data: Pre-loaded test data for easy testing
- Time Management: Departure and arrival times for flights

6. Error Handling
- Input validation for all user entries
- Exception handling for edge cases
- Meaningful error messages
- Prevention of duplicate entries
- Baggage weight limits enforcement

📁 File Structure

src/
├── Person.java           # Base class for people
├── Passenger.java        # Passenger model with validation
├── Flight.java           # Enhanced flight model
├── AirportManager.java   # Service layer for operations
└── AirportApp.java       # Main application with UI

Main Menu Options

1. Passenger Management
   - Add new passengers with validation
   - View all registered passengers
   - Search passengers by name
   - Remove passengers from system

2. Flight Management
   - Add new flights with seat configuration
   - View all scheduled flights
   - Search flights by destination
   - Update flight status
   - Remove flights

3. Boarding Operations
   - Board passengers onto flights
   - Select ticket class (Economy/Business)
   - View flight manifests

4. Reports & Statistics
   - Airport-wide statistics
   - Complete flight manifests
   - Occupancy rates

💡 Usage Examples

Adding a Passenger
1. Select "Passenger Management" → "Add New Passenger"
2. Enter name, age, address, and baggage weight
3. System validates baggage (max 20kg) and generates unique ID

Boarding a Passenger
1. Select "Boarding Operations" → "Board Passenger"
2. Enter passenger name or ID
3. Enter flight code
4. Select ticket class
5. System validates seat availability and confirms boarding

Viewing Flight Information
1. Select "Reports & Statistics" → "All Flight Manifests"
2. System displays all flights with:
   - Flight details (code, route, times)
   - Seat availability
   - Passenger list by class
   - Occupancy percentage

🔧 Technical Improvements

Code Quality
- Consistent naming conventions: camelCase for methods/variables
- Proper access modifiers: private fields, public methods
- Input validatio: All user inputs validated
- Resource management: Proper Scanner usage
- Collections: HashMap for O(1) lookups, ArrayList for lists

Design Patterns
- Service Layer Pattern: AirportManager handles business logic
- Factory Pattern: Passenger ID generation
- Enum Pattern: Flight status and ticket classes

Error Prevention
- Duplicate flight code prevention
- Passenger already boarded check
- Seat availability validation
- Flight status validation for boarding
- Baggage weight limits

📊 Sample Data
The application loads sample data on startup:
- 5 passengers with various attributes
- 3 flights with different routes
- Configured departure/arrival times

🎨 UI Enhancements
- Box-drawing characters for menus
- Clear section separators
- Status indicators (✓ for success, ✗ for errors)
- Formatted output for better readability
- Color-coded messages (if terminal supports)

🔐 Data Validation Rules
- Passenger Age: Must be positive integer
- Baggage Weight: Maximum 20kg, no negative values
- Flight Code: Automatically converted to uppercase
- Seat Numbers: Must be positive integers
- Passenger Names: Can contain spaces

🚦 Future Enhancements
- Database integration for persistence
- GUI implementation with JavaFX
- Ticket pricing system
- Gate assignment management
- Delay notifications
- Seat selection with seat maps
- Check-in system
- Boarding pass generation
- Multi-airport support
- Flight connections handling

📝 Notes
- The system maintains all data in memory (no persistence)
- Restart clears all data except sample data
- Passenger IDs are unique per session
- Flight codes must be unique
