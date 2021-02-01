package ro.ubb.lab6x.repository.XMLrepository;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import ro.ubb.lab6x.model.Client;
import ro.ubb.lab6x.model.Exceptions.ValidatorException;
import ro.ubb.lab6x.model.Song;
import ro.ubb.lab6x.model.Transaction;
import ro.ubb.lab6x.model.validators.Validator;

import java.util.Optional;

public class TransactionsXMLrepository extends XMLrepository<Transaction> {

    public TransactionsXMLrepository(Validator<Transaction> validator, String filepath) {
        super(validator, filepath);
    }

    @Override
    protected void buildEntityFromElement(Element element) {

        long id = Long.parseLong(getTextFromTagName(element, "id"));

        String title = getTextFromTagName(element, "title");
        int year = Integer.parseInt(getTextFromTagName(element, "year"));
        double price = Double.parseDouble(getTextFromTagName(element, "price"));
        Song song = new Song(title, year, price);
        long songId = Long.parseLong(getTextFromTagName(element, "ids"));
        song.setId(songId);

        long clientId = Long.parseLong(getTextFromTagName(element, "idc"));
        String firstName = getTextFromTagName(element, "first-name");
        String lastName = getTextFromTagName(element, "last-name");
        String adress = getTextFromTagName(element, "adress");
        Client client = new Client(firstName, lastName, adress);
        client.setId(clientId);


        Transaction transaction = new Transaction(client, song);
        transaction.setId(id);
        super.save(transaction);

    }

    @Override
    public Optional<Transaction> save(Transaction entity) throws ValidatorException {
        Optional<Transaction> optional = super.save(entity);
        saveToXml(entity);
        return optional;
    }

    @Override
    protected Element createNodeFromEntity(Transaction entity) {
        Element transactionElement = document.createElement("transaction");
        addChildWithTextContent(transactionElement,"id",String.valueOf(entity.getId()));

        Song song = entity.getSong();
        Node songNode = createNodeFromSong(song);

        Client client = entity.getClient();
        Node clientNode = createNodeFromClient(client);

        transactionElement.appendChild(clientNode);
        transactionElement.appendChild(songNode);

        return transactionElement;
    }

    private Node createNodeFromClient(Client client) {
        Element clientElement = document.createElement("client");
        addChildWithTextContent(clientElement,"idc",String.valueOf(client.getId()));
        addChildWithTextContent(clientElement,"first-name",client.getFirstName());
        addChildWithTextContent(clientElement,"last-name",client.getLastName());
        addChildWithTextContent(clientElement,"adress",client.getAdress());
        return clientElement;
    }

    private Node createNodeFromSong(Song song) {
        Element songElement = document.createElement("song");
        addChildWithTextContent(songElement,"ids",String.valueOf(song.getId()));
        addChildWithTextContent(songElement,"title",song.getTitle());
        addChildWithTextContent(songElement,"year",Integer.toString(song.getYearOfRelease()));
        addChildWithTextContent(songElement,"price",String.valueOf(song.getPrice()));
        return songElement;
    }
}
