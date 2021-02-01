package ro.ubb.lab6x.repository.XMLrepository;

import org.w3c.dom.Element;
import ro.ubb.lab6x.model.Client;
import ro.ubb.lab6x.model.Exceptions.ValidatorException;
import ro.ubb.lab6x.model.validators.Validator;
import java.util.Optional;

public class ClientXMLrepository extends XMLrepository<Client> {

    public ClientXMLrepository(Validator<Client> validator, String filepath) {
        super(validator, filepath);
    }

    @Override
    protected void buildEntityFromElement(Element clientElement) {
        Long id = Long.parseLong(getTextFromTagName(clientElement,"id"));
        String firstName = getTextFromTagName(clientElement, "first-name");
        String lastName = getTextFromTagName(clientElement, "last-name");
        String address = getTextFromTagName(clientElement, "adress");

        Client client = new Client(firstName,lastName,address);
        client.setId(id);
        super.save(client);
    }

    @Override
    public Optional<Client> save(Client entity) throws ValidatorException {
        Optional<Client> optional = super.save(entity);
        saveToXml(entity);
        return optional;
    }

    @Override
    protected Element createNodeFromEntity(Client entity) {
        Element clientElement = document.createElement("client");
        addChildWithTextContent(clientElement,"id",String.valueOf(entity.getId()));
        addChildWithTextContent(clientElement,"first-name",entity.getFirstName());
        addChildWithTextContent(clientElement,"last-name",entity.getLastName());
        addChildWithTextContent(clientElement,"adress",entity.getAdress());

        return clientElement;
    }

}
