# Railway Reservation and Network Management System

A standalone Java Swing application for managing railway reservations, querying routes, and administrating a railway network graph.

## Features
- **User Roles:** Distinct features for Administrators and standard Users.
- **Network Graph:** Custom graph implementation to model stations and tracks.
- **Route Finding:** Uses Dijkstra's algorithm to find optimal paths between stations.
- **Ticket Booking:** Full ticketing lifecycle and schedules management.
- **GUI:** Built with Java Swing.
- **File Persistence:** Stores data locally in text files under the `data/` directory.

## Project Structure
- `src/`: Java source code.
- `test/`: JUnit test cases.
- `data/`: Local storage for the file persistence system.
- `bin/`: Compiled bytecode (generated upon compilation).
- `build.gradle`: Gradle build configuration.

## Requirements
- Java 17 or higher
- Gradle (optional, if you want to use the build tool)

## How to Run
### Using standard Java (if Gradle is not installed)
1. Compile the code:
   ```bash
   javac -d bin -sourcepath src src/Main.java src/**/*.java
   ```
2. Run the application:
   ```bash
   java -cp bin Main
   ```

### Using Gradle
```bash
gradle run
```

## Running Tests
Run the JUnit test suite via Gradle:
```bash
gradle test
```
