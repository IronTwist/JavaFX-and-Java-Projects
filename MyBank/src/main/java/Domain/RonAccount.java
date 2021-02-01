package Domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class RonAccount extends Entity {

    private int clientId;
    private double sold;
    private DateTime timestampCreated;

    public RonAccount(int clientId, double sold, DateTime timestampCreated) {
        this.clientId = clientId;
        this.sold = sold;
        this.timestampCreated = timestampCreated;
    }

    public RonAccount(int id, int clientId, double sold, DateTime timestampCreated) {
        super(id);
        this.clientId = clientId;
        this.sold = sold;
        this.timestampCreated = timestampCreated;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public double getSold() {
        return sold;
    }

    public void setSold(double sold) {
        this.sold = sold;
    }

    public DateTime getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(DateTime timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    @Override
    public String toString() {
        return "RonAccount{" +
                "id=" + super.getId() +
                ", clientId=" + clientId +
                ", sold=" + sold +
                ", timestampCreated=" + timestampCreated +
                '}';
    }
}
