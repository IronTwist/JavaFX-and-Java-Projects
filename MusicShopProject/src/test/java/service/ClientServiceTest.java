//package service;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ro.ubb.lab6x.model.Client;
//import ro.ubb.lab6x.model.Song;
//import ro.ubb.lab6x.model.validators.ClientValidator;
//import ro.ubb.lab6x.model.validators.SongValidator;
//import ro.ubb.lab6x.model.validators.Validator;
//import ro.ubb.lab6x.repository.CSVrepository.ClientFileSeverRepository;
//import ro.ubb.lab6x.repository.CSVrepository.SongFileSeverRepository;
//import ro.ubb.lab6x.service.ClientService;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ClientServiceTest {
//
//    Validator<Client> clientValidator = new ClientValidator();
//    Validator<Song> songValidator = new SongValidator();
//    ClientFileSeverRepository clientFileSeverRepository = new ClientFileSeverRepository(clientValidator, "db/ClientsTest.csv");
//    SongFileSeverRepository songFileSeverRepository = new SongFileSeverRepository(songValidator,"db/SongsTest.csv");
//    ClientService clientService = new ClientService(clientFileSeverRepository);
//
//    Client client1 = new Client("Marin","Sorescu", "Cluj");
//    Client client2 = new Client("Ion","Ianolide", "Cluj");
//
//    @BeforeEach
//    void setUp() {
//        client1.setId(33L);
//        client2.setId(41L);
//    }
//
//    @Test
//    void findAll(){
//        System.out.println("findAll()");
//
//        List<Client> expectedList = new ArrayList<>();
//        expectedList.add(client2);
//        expectedList.add(client1);
//
//        assertEquals(expectedList.toString(), clientService.findAll().toString());
//
//    }
//
//    @Test
//    void findOne() {
//        System.out.println("findOne()");
//
//        Optional<Client> find = clientService.findOne(33L);
//        System.out.println(find.isPresent());
//
//        assertTrue(find.isPresent());
//    }
//
//    @Test
//    void updateClient() {
//        System.out.println("updateClient()");
//
//        Client client = clientService.findOne(33L).get();
//        System.out.println("Before: " + client);
//        clientService.updateClient(33L,"Marin", "Sorescu", "Cluj");
//
//        assertEquals("Marin", clientService.findOne(33L).get().getFirstName());
//
//        System.out.println("After: " + clientService.findOne(33L).get());
//    }
//
//    @Test
//    void addClient(){
//        System.out.println("addClient()");
//
//        clientService.add(client2);
//        assertEquals(client2, clientService.findOne(41L).get());
//
//    }
//
//    @Test
//    void delete(){
//        System.out.println("delete()");
//
//        clientService.delete(41L);
//        Optional<Client> find = clientService.findOne(41L);
//        System.out.println(find.isEmpty());
//        assertTrue(find.isEmpty());
//        clientService.add(client2);
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//}