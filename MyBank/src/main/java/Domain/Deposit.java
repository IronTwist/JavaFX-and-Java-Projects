package Domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Deposit extends Entity {

    private int idClient;
    private double sold;
    private double percent;
    private int periodMonths;
    private double soldFinalDeposit;
    private DateTime timestamp;
    private DateTime timestampDepositCreated;


    public Deposit(int idClient, double sold, double percent, int periodMonths, double soldFinalDeposit, DateTime timestamp, DateTime timestampDepositCreated) {
        this.idClient = idClient;
        this.sold = sold;
        this.percent = percent;
        this.periodMonths = periodMonths;
        this.soldFinalDeposit = soldFinalDeposit;
        this.timestamp = timestamp;
        this.timestampDepositCreated =  timestampDepositCreated;

    }

    public Deposit(int id, int idClient, double sold, double percent, int periodMonths, double soldFinalDeposit, DateTime timestamp, DateTime timestampDepositCreated) {
        super(id);
        this.idClient = idClient;
        this.sold = sold;
        this.percent = percent;
        this.periodMonths = periodMonths;
        this.soldFinalDeposit = soldFinalDeposit;
        this.timestamp = timestamp;
        this.timestampDepositCreated =  timestampDepositCreated;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public double getSold() {
        return sold;
    }

    public void setSold(double sold) {
        this.sold = sold;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public int getPeriodMonths() {
        return periodMonths;
    }

    public void setPeriodMonths(int periodMonths) {
        this.periodMonths = periodMonths;
    }

    public double getSoldFinalDeposit() {
        return soldFinalDeposit;
    }

    public void setSoldFinalDeposit(double soldFinalDeposit) {
        this.soldFinalDeposit = soldFinalDeposit;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public DateTime getTimestampDepositCreated() {
        return timestampDepositCreated;
    }

    public void setTimestampDepositCreated(DateTime timestampDepositCreated) {
        this.timestampDepositCreated = timestampDepositCreated;
    }
}
