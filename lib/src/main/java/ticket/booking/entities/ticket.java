package ticket.booking.entities;
import java.util.Date ;

public class ticket {

    private String ticketId;

    private String userId;

    private String source;

    private String destination;
    private Date dateofTravel;

    private Train train;
    public ticket(){}

    public ticket(String ticketId, String userId, String source, String destination, Date dateofTravel, Train train) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.source = source;
        this.destination = destination;
        this.dateofTravel = dateofTravel;
        this.train = train;
    }
    public String getTicketInfo(){
        return String.format("Ticket ID: %s belongs to User %s to %s", ticketId,userId,source,destination,dateofTravel);
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDateofTravel() {
        return dateofTravel;
    }

    public void setDateofTravel(Date dateofTravel) {
        this.dateofTravel = dateofTravel;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }
}