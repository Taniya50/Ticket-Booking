
package ticket.booking;

import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.service.UserBookingService;
import ticket.booking.util.UserServiceUtil;

import java.io.IOException;
import java.sql.Time;
import java.util.*;

public class app {
    public static void main(String[] args) {
        System.out.println("Running Train Booking System");
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService;

        try {
            userBookingService = new UserBookingService();
        } catch (IOException ex) {
            System.out.println("There is something wrong");
            return;
        }

        Train trainSelectedForBooking = null; // Declare trainSelectedForBooking here

        while (option != 7) {
            System.out.println("Choose an option:");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Booking");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a seat");
            System.out.println("6. Cancel my Booking");
            System.out.println("7. Exit the App");
            option = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (option) {
                case 1:
                    System.out.println("Enter the Username to sign up:");
                    String nameToSignup = scanner.nextLine();
                    System.out.println("Enter the password to sign up:");
                    String passwordToSignup = scanner.nextLine();

                    boolean signupStatus = userBookingService.signUp(nameToSignup, passwordToSignup);
                    if (signupStatus) {
                        System.out.println("Signup successful!");
                    } else {
                        System.out.println("Signup failed! User may already exist.");
                    }
                    break;

                case 2:
                    System.out.println("Enter the Username to login:");
                    String nameToLogin = scanner.nextLine();
                    System.out.println("Enter the password to login:");
                    String passwordToLogin = scanner.nextLine();

                    try {
                        userBookingService = new UserBookingService(new User(
                                nameToLogin,
                                passwordToLogin,
                                UserServiceUtil.hashPassword(passwordToLogin),
                                new ArrayList<>(),
                                UUID.randomUUID().toString()
                        ));
                        System.out.println("Login successful!");
                    } catch (IOException ex) {
                        System.out.println("Error logging in. Please try again.");
                    }
                    break;

                case 3:
                    System.out.println("Fetching your booking...");
                    userBookingService.fetchBooking();
                    break;

                case 4:
                    System.out.println("Type your source station:");
                    String source = scanner.nextLine();
                    System.out.println("Type your destination station:");
                    String dest = scanner.nextLine();

                    List<Train> trains = userBookingService.getTrains(source, dest);
                    if (trains.isEmpty()) {
                        System.out.println("No trains found for the selected route.");
                        break;
                    }

                    int index = 1;
                    for (Train t : trains) {
                        System.out.println(index + ". Train ID: " + t.getTrainId());
                        for (Map.Entry<String, Time> entry : t.getStationTimes().entrySet()) {
                            System.out.println("Station: " + entry.getKey() + " | Time: " + entry.getValue());
                        }
                        index++;
                    }

                    System.out.println("Select a train by typing 1,2,3...");
                    int trainIndex = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character

                    if (trainIndex < 1 || trainIndex > trains.size()) {
                        System.out.println("Invalid train selection.");
                        break;
                    }

                    trainSelectedForBooking = trains.get(trainIndex - 1);
                    System.out.println("You have selected Train ID: " + trainSelectedForBooking.getTrainId());
                    break;

                case 5:
                    if (trainSelectedForBooking == null) {
                        System.out.println("Please select a train first by using option 4.");
                        break;
                    }

                    System.out.println("Select a seat from the available seats:");
                    List<List<Integer>> seats = userBookingService.fetchSeats(trainSelectedForBooking);

                    for (int i = 0; i < seats.size(); i++) {
                        for (int j = 0; j < seats.get(i).size(); j++) {
                            System.out.print(seats.get(i).get(j) + " ");
                        }
                        System.out.println();
                    }

                    System.out.println("Enter the row number:");
                    int row = scanner.nextInt();
                    System.out.println("Enter the column number:");
                    int col = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character

                    boolean bookingStatus = userBookingService.bookSeat(trainSelectedForBooking, row, col);
                    if (bookingStatus) {
                        System.out.println("Seat booked successfully!");
                    } else {
                        System.out.println("Failed to book seat. It may be unavailable.");
                    }
                    break;

                case 6:
                    System.out.println("Enter the ticket ID to cancel:");
                    String ticketId = scanner.nextLine();
                    boolean cancelStatus = userBookingService.cancelBooking(ticketId);
                    if (cancelStatus) {
                        System.out.println("Booking canceled successfully.");
                    } else {
                        System.out.println("Failed to cancel booking. Ticket ID may be invalid.");
                    }
                    break;

                case 7:
                    System.out.println("Exiting the app. Thank you for using Train Booking System.");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        scanner.close();
    }
}
