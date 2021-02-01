//package service;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ro.ubb.lab6x.model.Client;
//import ro.ubb.lab6x.model.Song;
//import ro.ubb.lab6x.model.Transaction;
//import ro.ubb.lab6x.model.validators.ClientValidator;
//import ro.ubb.lab6x.model.validators.SongValidator;
//import ro.ubb.lab6x.model.validators.TransactionValidator;
//import ro.ubb.lab6x.model.validators.Validator;
//import ro.ubb.lab6x.repository.XMLrepository.ClientXMLrepository;
//import ro.ubb.lab6x.repository.XMLrepository.SongXMLrepository;
//import ro.ubb.lab6x.repository.XMLrepository.TransactionsXMLrepository;
//import ro.ubb.lab6x.service.ClientService;
//import ro.ubb.lab6x.service.SongService;
//import ro.ubb.lab6x.service.TransactionService;
//
//import java.util.*;
//
//import java.util.concurrent.atomic.AtomicReference;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class TransactionServiceTest {
//    Validator<Client> clientValidator = new ClientValidator();
//    Validator<Song> songValidator = new SongValidator();
//    Validator<Transaction> transactionValidator = new TransactionValidator();
//
//    SongXMLrepository songXMLrepository = new SongXMLrepository(songValidator,"db/Songs.xml");
//    ClientXMLrepository clientXMLrepository = new ClientXMLrepository(clientValidator, "db/Clients.xml");
//    TransactionsXMLrepository transactionsXMLrepository = new TransactionsXMLrepository(transactionValidator,"db/TransactionsTest.xml");
//
//    SongService songService = new SongService(songXMLrepository);
//    ClientService clientService = new ClientService(clientXMLrepository);
//    TransactionService transactionService = new TransactionService(transactionsXMLrepository);
//
//    AtomicReference<Song> songAtomicReference = new AtomicReference<>();
//    AtomicReference<Client> clientAtomicReference = new AtomicReference<>();
//
//    long transactionId = 0;
//    long songId = 0;
//    long clientId = 0;
//
//    @BeforeEach
//    void setUp() {
//        transactionId = 234;
//        songId = 1082;
//        clientId = 123;
//    }
//
//    @Test
//    void addTransaction() {
//        songService.findOne(songId).ifPresent(songAtomicReference::set);
//        clientService.findOne(clientId).ifPresent(clientAtomicReference::set);
//        Transaction transaction = new Transaction(clientAtomicReference.get(),songAtomicReference.get());
//        transaction.setId(transactionId);
//
//        transactionService.add(transaction);
//
//        assertEquals(Optional.of(transaction),transactionService.findOne(234));
//
//    }
//
//    /**
//     * findAll() compares by given transaction id
//     */
//    @Test
//    void findAll () {
//        long[] idListSearch = new long[]{90, 1, 2, 123};
//        List<Long> idListFound = new ArrayList<>();
//
//        Iterable<Transaction> transactions = transactionService.findAll();
//
//        for(Transaction tr: transactions){
//            idListFound.add((tr.getId()));
//        }
//
//        assertEquals(Arrays.toString(idListSearch),Arrays.toString(idListFound.toArray()));
//
//    }
//
//    @Test
//    void findOne() {
//
//        songService.findOne(songId).ifPresent(songAtomicReference::set);
//        clientService.findOne(clientId).ifPresent(clientAtomicReference::set);
//        Transaction transaction = new Transaction(clientAtomicReference.get(),songAtomicReference.get());
//        transaction.setId(transactionId);
//
//        transactionService.add(transaction);
//
//        Optional<Transaction> find = transactionService.findOne(234);
//
//        assertTrue(find.isPresent());
//    }
//
//    @Test
//    void deleteTransaction() {
//
//        songService.findOne(songId).ifPresent(songAtomicReference::set);
//        clientService.findOne(clientId).ifPresent(clientAtomicReference::set);
//        Transaction transaction = new Transaction(clientAtomicReference.get(),songAtomicReference.get());
//        transaction.setId(transactionId);
//
//        transactionService.add(transaction);
//        transactionService.delete(234);
//
//        Optional<Transaction> addedTransaction = transactionService.findOne(234);
//
//        assertFalse(addedTransaction.isPresent());
//    }
//
//    /**
//     * Update is not implemented in TransactionService because is not required.
//     * update is made on transactionsXMLrepository.update()
//     */
//    @Test
//    void updateTransaction() {
//        songService.findOne(songId).ifPresent(songAtomicReference::set);
//        clientService.findOne(clientId).ifPresent(clientAtomicReference::set);
//        Transaction transaction = new Transaction(clientAtomicReference.get(),songAtomicReference.get());
//        transaction.setId(transactionId);
//
//        transactionService.add(transaction);
//
//        Optional<Transaction> updateTransaction = transactionService.findOne(234);
//        Transaction tr = updateTransaction.orElse(null);
//
//        assert tr != null;
//        tr.getClient().setFirstName("Testare update");
//
//        transactionsXMLrepository.update(tr);
//
//        Optional<Transaction> afterUpdate = transactionService.findOne(234);
//        Transaction trAfterUpdate = afterUpdate.orElse(null);
//
//        assert trAfterUpdate != null;
//        assertEquals("Testare update", trAfterUpdate.getClient().getFirstName());
//    }
//
//    @AfterEach
//    void afterEach() {
//
//        transactionService.delete(234);
//
//    }
//}