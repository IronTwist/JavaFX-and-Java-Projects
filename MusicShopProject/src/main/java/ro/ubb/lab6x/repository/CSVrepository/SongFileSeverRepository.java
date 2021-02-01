package ro.ubb.lab6x.repository.CSVrepository;

import ro.ubb.lab6x.model.Exceptions.ValidatorException;
import ro.ubb.lab6x.model.Song;
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

public class SongFileSeverRepository extends InMemoryRepository<Long, Song> {
    private String fileName;

    public SongFileSeverRepository(Validator<Song> validator, String fileName) {
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
                String title = items.get(1);
                int yearOfRelease = Integer.parseInt(items.get(2));
                double price = Double.parseDouble(items.get(3));

                Song song = new Song(title, yearOfRelease, price);
                song.setId(id);

                try {
                    super.save(song);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Song> save(Song entity) throws ValidatorException {
        Optional<Song> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Song entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getTitle() + "," + entity.getYearOfRelease() + "," + entity.getPrice());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Song> update(Song entity) throws ValidatorException {
        Optional<Song> optional =  super.update(entity);
        replaceContentInfile();
        return optional;
    }

    @Override
    public Optional<Song> delete(Long aLong) {
        Optional<Song> optional =  super.delete(aLong);
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
}