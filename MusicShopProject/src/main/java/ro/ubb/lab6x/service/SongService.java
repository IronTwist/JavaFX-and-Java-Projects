package ro.ubb.lab6x.service;

import ro.ubb.lab6x.model.Client;
import ro.ubb.lab6x.model.Song;
import ro.ubb.lab6x.repository.Paging.PagingRepository;
import ro.ubb.lab6x.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class SongService extends AbstractService<Song> {
    public SongService(PagingRepository<Long, Song> repository) {
        super(repository);
    }

    public Optional<Song> updateSong(long id, String title, int yearOfRelease, double price) {
        Song song = repository.findOne(id).get();
        song.setTitle(title);
        song.setYearOfRelease(yearOfRelease);
        song.setPrice(price);
        return repository.update(song);
    }

    @Override
    public Iterable<Song> search(String searchString) {
        Iterable<Song> songs = repository.findAll();
        List<Song> foundList = new ArrayList<>();

        songs.forEach(song -> {
            if(song.getTitle().toLowerCase().contains(searchString.toLowerCase())){
                foundList.add(song);
            }
        });
        
        return foundList;
    }
}
