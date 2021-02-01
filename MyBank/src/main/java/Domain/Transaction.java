package Domain;

public class Transaction extends  Entity{

    private int clientId;
    private DateTime timestamp;
    private double amountTransaction;
    private String type;    //Debit/Credit  or withdraw/deposit

    public Transaction(int clientId, DateTime timestamp, double amountTransaction, String type) {
        this.clientId = clientId;
        this.timestamp = timestamp;
        this.amountTransaction = amountTransaction;
        this.type = type;
    }

    public Transaction(int id, int clientId, DateTime timestamp, double amountTransaction, String type) {
        super(id);
        this.clientId = clientId;
        this.timestamp = timestamp;
        this.amountTransaction = amountTransaction;
        this.type = type;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmountTransaction() {
        return amountTransaction;
    }

    public void setAmountTransaction(double amountTransaction) {
        this.amountTransaction = amountTransaction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "clientId='" + clientId + '\'' +
                ", timestamp=" + timestamp +
                ", amountTransaction=" + amountTransaction +
                ", type='" + type + '\'' +
                '}';
    }
}
