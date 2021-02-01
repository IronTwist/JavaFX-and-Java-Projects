package ro.ubb.lab6x.repository.CSVrepository;

import ro.ubb.lab6x.model.Client;
import ro.ubb.lab6x.model.Exceptions.ValidatorException;
import ro.ubb.lab6x.model.validators.Validator;
import ro.ubb.lab6x.repository.InMemoryRepository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ClientFileSeverRepository extends InMemoryRepository<Long, Client> {
    private String fileName;

    public ClientFileSeverRepository(Validator<Client> validator , String fileName) {
        super(validator);
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {

                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));
                String firstName = items.get(1);
                String lastName = items.get((2));
                String adress = items.get(3);

                Client client = new Client(firstName,lastName,adress);
                client.setId(id);

                try {
                    super.save(client);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Client> save(Client entity) throws ValidatorException {
        Optional<Client> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Client entity) {
        Path path = Paths.get(fileName);
        File file = new File(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getFirstName() + "," + entity.getLastName() + "," + entity.getAdress());
            bufferedWriter.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Client> update(Client entity) throws ValidatorException {
        Optional<Client> clientOptional = super.update(entity);
        replaceContentInFile();
        return  clientOptional;
    }

    @Override
    public Optional<Client> delete(Long aLong) {
        Optional<Client> clientOptional = super.delete(aLong);
        replaceContentInFile();
        return  clientOptional;
    }

    private void replaceContentInFile() {
        try (FileWriter fileWriter = new FileWriter(fileName,false)) {
            super.findAll().forEach(this::saveToFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
