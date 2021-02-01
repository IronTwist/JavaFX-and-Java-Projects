package ro.ubb.lab6x.model.validators;

import ro.ubb.lab6x.model.Exceptions.ValidatorException;
import ro.ubb.lab6x.model.Transaction;

public class TransactionValidator implements Validator<Transaction> {
    @Override
    public void validate(Transaction entity) {
       StringBuilder stringBuilder = new StringBuilder();
       if(entity.getSong() == null){
           stringBuilder.append("Song not found\n");
       }
       if(entity.getClient() == null){
           stringBuilder.append("Client not found");
       }
       if (!stringBuilder.toString().isEmpty()){
           throw new ValidatorException(stringBuilder.toString());
       }
    }
}
