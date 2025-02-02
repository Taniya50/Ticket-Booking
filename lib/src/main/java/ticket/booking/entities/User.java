package ticket.booking.entities;

import java.util.List;

public class User {
    private String name;
    private String password;
    private String hashPassword;
    private List<ticket> ticketsBooked;
    private String UserId;

    public User(String name, String password, String hashPassword, List<ticket> ticketsBooked, String UserId) {
        this.name = name;
        this.password = password;
        this.hashPassword = hashPassword;
        this.ticketsBooked = ticketsBooked;
        this.UserId = UserId;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public String getHashedPassword() {
        return hashPassword;
    }

    public String getPassword() {
        return password;
    }

    public List<ticket> getTicketsBooked() {
        return ticketsBooked;
    }

    public String getUserId() {
        return UserId;
    }

    public void printTicket() {
        for (int i = 0; i < ticketsBooked.size(); i++) {
            System.out.println(ticketsBooked.get(i).getTicketInfo());
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public void setTicketsBooked(List<ticket> ticketsBooked) {
        this.ticketsBooked = ticketsBooked;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    // Change return type to List<ticket>
    public List<ticket> getTickets() {
        return ticketsBooked; // Returning List<ticket> directly
    }
}

