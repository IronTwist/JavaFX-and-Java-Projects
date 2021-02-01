package ro.ubb.lab6x.model;

import java.util.Objects;

public class Transaction extends Entity<Long> {
    Client client;
    Song song;

    public Transaction(Client client, Song song) {
        this.client = client;
        this.song = song;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return getClient().equals(that.getClient()) &&
                getSong().equals(that.getSong());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClient(), getSong());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "client=" + client +
                ", song=" + song +
                '}';
    }
}
