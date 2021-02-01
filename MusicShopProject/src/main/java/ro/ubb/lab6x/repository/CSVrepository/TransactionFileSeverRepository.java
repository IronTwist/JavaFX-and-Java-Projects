package ro.ubb.lab6x.repository.CSVrepository;

import ro.ubb.lab6x.model.Client;
import ro.ubb.lab6x.model.Exceptions.ValidatorException;
import ro.ubb.lab6x.model.Song;
import ro.ubb.lab6x.model.Transaction;
import ro.ubb.lab6x.model.validators.Validator;
import ro.ubb.lab6x.repository.InMemoryRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TransactionFileSeverRepository extends InMemoryRepository<Long, Transaction> {
    private String fileName;


    public TransactionFileSeverRepository(Validator<Transaction> validator,String fileName) {
        super(validator);

        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {

                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.parseLong(items.get(0));
                Long clientId = Long.parseLong(items.get(1));
                String firstName = items.get(2);
                String lastName = items.get((3));
                String adress = items.get(4);
                Long songId = Long.valueOf(items.get(5));
                String title = items.get(6);
                int yearOfRelease = Integer.parseInt(items.get(7));
                double price = Double.parseDouble(items.get(8));
                Song song = new Song(title,yearOfRelease,price);
                song.setId(songId);
                Client client = new Client(firstName,lastName,adress);
                client.setId(clientId);
                Transaction transaction = new Transaction(client,song);
                transaction.setId(id);

                try {
                    super.save(transaction);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Transaction> save(Transaction entity) throws ValidatorException {
        Optional<Transaction> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Transaction entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + ","
                            + entity.getClient().getId()+ ","
                            + entity.getClient().getFirstName()+ ","
                            + entity.getClient().getLastName()+ ","
                            + entity.getClient().getAdress()+ ","
                            + entity.getSong().getId()+ ","
                            + entity.getSong().getTitle()+ ","
                            + entity.getSong().getYearOfRelease()+ ","
                            + entity.getSong().getPrice()+ ","
                            + entity.getSong().getId());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Transaction> delete(Long aLong) {
        Optional<Transaction> optional =  super.delete(aLong);
        replaceContentInfile();
        return  optional;
    }

    private void replaceContentInfile() {
        try (FileWriter fileWriter = new FileWriter(fileName,false)) {
            super.findAll().forEach(this::saveToFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Transaction> update(Transaction entity) throws ValidatorException {
        Optional<Transaction> optional =  super.update(entity);
        replaceContentInfile();
        return optional;
    }

}
