package ro.ubb.lab6x.model.validators;

import ro.ubb.lab6x.model.Client;
import ro.ubb.lab6x.model.Exceptions.ValidatorException;

public class ClientValidator implements Validator<Client> {
    @Override
    public void validate(Client entity) {
        StringBuilder stringBuilder = new StringBuilder("");
        if(entity.getFirstName().trim().isEmpty() || entity.getLastName().trim().isEmpty()){
            stringBuilder.append("A client must have a first name and a last name\n");
        }
        if(entity.getAdress().trim().isEmpty()){
            stringBuilder.append("adress cannot be empty!\n");
        }
        if(!stringBuilder.toString().equals("")){
            throw new ValidatorException(stringBuilder.toString());
        }

    }
}
