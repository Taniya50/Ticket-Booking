
package ticket.booking.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;
import ticket.booking.service.TrainService;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UserBookingService {

    private User user;
    private List<User> userList;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String USER_PATH = "app/src/main/java/ticket/booking/localDb/users.json";

    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        loadUsers();
    }

    public UserBookingService() throws IOException {
        loadUsers();
    }

    // Load users from file
    private void loadUsers() throws IOException {
        File users = new File(USER_PATH);
        if (users.exists()) {
            userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
        } else {
            userList = new ArrayList<>();
        }
    }

    // Fetch available trains
    public List<Train> getTrains(String source, String destination) {
        TrainService trainService = new TrainService(); // Ensure TrainService exists
        return trainService.searchTrains(source, destination);
    }

    // Fetch available seats for a train
    public List<List<Integer>> fetchSeats(Train train) {
        if (train == null) {
            System.out.println("Invalid train selection.");
            return new ArrayList<>();
        }
        return train.getSeats(); // Ensure Train class has a getSeats() method
    }

    // Book a seat on a train
    public boolean bookSeat(Train train, int row, int col) {
        if (train == null) {
            System.out.println("Invalid train selection.");
            return false;
        }

        List<List<Integer>> seats = train.getSeats(); // Get current seat map
        if (row >= seats.size() || col >= seats.get(row).size()) {
            System.out.println("Invalid seat selection.");
            return false;
        }

        if (seats.get(row).get(col) == 1) { // Assuming 1 means booked
            System.out.println("Seat already booked. Choose another seat.");
            return false;
        }

        seats.get(row).set(col, 1); // Mark seat as booked
        System.out.println("Seat successfully booked!");
        return true;
    }

    // Sign Up Method
    public Boolean signUp(String name, String password) {
        try {
            String hashedPassword = UserServiceUtil.hashPassword(password);
            User newUser = new User(name, password, hashedPassword, new ArrayList<>(), UUID.randomUUID().toString());

            // Check if user already exists
            Optional<User> existingUser = userList.stream()
                    .filter(user -> user.getName().equalsIgnoreCase(name))
                    .findFirst();

            if (existingUser.isPresent()) {
                System.out.println("User already exists! Try logging in.");
                return false;
            }

            userList.add(newUser);
            saveUserListToFile();
            System.out.println("User signed up successfully!");
            return true;
        } catch (IOException ex) {
            System.out.println("Error signing up: " + ex.getMessage());
            return false;
        }
    }

    // Cancel Booking Method
    public Boolean cancelBooking(String ticketId) {
        if (user == null) {
            System.out.println("No user logged in. Please log in first.");
            return false;
        }

        boolean removed = user.getTickets().removeIf(ticket -> ticket.getClass().equals(ticketId));

        if (removed) {
            try {
                saveUserListToFile();
                System.out.println("Booking canceled successfully.");
                return true;
            } catch (IOException e) {
                System.out.println("Error canceling booking: " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("No booking found with Ticket ID: " + ticketId);
            return false;
        }
    }

    // Save updated user list to file
    private void saveUserListToFile() throws IOException {
        File userFile = new File(USER_PATH);
        objectMapper.writeValue(userFile, userList);
    }

    // Fetch booking details
    public void fetchBooking() {
        if (user != null) {
            user.printTicket();
        } else {
            System.out.println("No user is logged in.");
        }
    }
}
