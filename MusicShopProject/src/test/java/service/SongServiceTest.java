package service;

import org.junit.jupiter.api.*;
import ro.ubb.lab6x.model.Song;
import ro.ubb.lab6x.model.validators.SongValidator;
import ro.ubb.lab6x.model.validators.Validator;
import ro.ubb.lab6x.repository.CSVrepository.SongFileSeverRepository;

import ro.ubb.lab6x.service.SongService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SongServiceTest {

    Validator<Song> songValidator = new SongValidator();
    SongFileSeverRepository songFileSeverRepository = new SongFileSeverRepository(songValidator, "db/SongsTest.csv");
    SongService songService = new SongService(songFileSeverRepository);
    Song song1 = new Song("Below the low", 2011, 14.0);
    Song song2 = new Song("other song", 2010, 14.0);

    @BeforeEach
    void setUp() {

        song1.setId(12L);
        song2.setId(23L);
    }

    @Test
    void addSong() {
        System.out.println("addSong()");

        // song1.setId(24L); //Expected 24
        assertEquals(Optional.of(song1), songService.findOne(12L));

    }

    @Test

    void findAll() {
        System.out.println("findAll()");
        Set<Song> songs = new HashSet<>();

        songs.add(song2);
        songs.add(song1);
        songService.add(song2);
        songService.add(song1);
        System.out.println(songService.findAll());
        assertEquals(songs ,songService.findAll());
        songService.delete(23L);

    }

    @Test
    void findOne() {
        System.out.println("findOne()");
        Optional<Song> find = songService.findOne(12L);
        System.out.println(find.isPresent());
        assertTrue(find.isPresent());
    }

    @Test
    void updateSong() {
        System.out.println("updateSong()");
        Song song = songService.findOne(12L).get();
        System.out.println("Before: " + song);
        songService.updateSong(12L, "Below the Law", 2011, 14.0);


        assertEquals("Below the Law", songService.findOne(12L).get().getTitle());
        assertEquals(2011, songService.findOne(12L).get().getYearOfRelease());

        System.out.println("After: " + songService.findOne(12L).get());
    }

    @Test
    void delete() {
        System.out.println("delete()");
        songService.delete(12L);
        Optional<Song> find = songService.findOne(12L);
        System.out.println(find.isEmpty());
        assertTrue(find.isEmpty());
        songService.add(song1);
    }

    @AfterEach
    void tearDown() {

    }
}