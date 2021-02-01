package ro.ubb.lab6x.model;

public class ClientSongCountViewModel {
    private Client client;
    private int songCount;

    public ClientSongCountViewModel(Client client, int songCount) {
        this.client = client;
        this.songCount = songCount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getSongCount() {
        return songCount;
    }

    public void setSongCount(int songCount) {
        this.songCount = songCount;
    }

    @Override
    public String toString() {
        return "ClientSongCountViewModel{" +
                "client=" + client +
                ", songCount=" + songCount +
                '}';
    }
}
