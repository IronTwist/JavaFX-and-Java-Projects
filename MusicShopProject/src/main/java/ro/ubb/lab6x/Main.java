package ro.ubb.lab6x;

import ro.ubb.lab6x.model.Client;
import ro.ubb.lab6x.model.Song;
import ro.ubb.lab6x.UI.Console;
import ro.ubb.lab6x.model.Transaction;
import ro.ubb.lab6x.model.validators.ClientValidator;
import ro.ubb.lab6x.model.validators.SongValidator;
import ro.ubb.lab6x.model.validators.TransactionValidator;
import ro.ubb.lab6x.repository.CSVrepository.ClientFileSeverRepository;
import ro.ubb.lab6x.repository.CSVrepository.SongFileSeverRepository;
import ro.ubb.lab6x.repository.CSVrepository.TransactionFileSeverRepository;
import ro.ubb.lab6x.repository.Repository;
import ro.ubb.lab6x.repository.SQLrepository.SongSQLrepository;
import ro.ubb.lab6x.repository.SQLrepository.TransactionSQLrepository;
import ro.ubb.lab6x.service.ClientService;
import ro.ubb.lab6x.service.SongService;
import ro.ubb.lab6x.model.validators.Validator;
import ro.ubb.lab6x.service.TransactionService;

public class Main {
    public static void main(String[] args) {
        String URL = System.getProperty("url");
        String password = System.getProperty("password");
        String user = System.getProperty("user");

        Validator<Client> clientValidator = new ClientValidator();
        Validator<Song> songValidator = new SongValidator();
        Validator<Transaction> transactionValidator = new TransactionValidator();

        Repository<Long,Song> songSQLRepository = new SongSQLrepository(URL,user,password,"tb_songs",songValidator);
      //  Repository<Long,Client> clientSQLRepository = new ClientSQLrepository(URL, user, password, "tb_clients", clientValidator);
        Repository<Long, Transaction> transactionSQLRepository = new TransactionSQLrepository(URL,user,password,"vw_transactions",transactionValidator);

     //   SongXMLrepository songXMLrepository = new SongXMLrepository(songValidator,"db/Songs.xml");
     //   ClientXMLrepository clientXMLrepository = new ClientXMLrepository(clientValidator, "db/Clients.xml");
     //   TransactionsXMLrepository transactionsXMLrepository = new TransactionsXMLrepository(transactionValidator,"db/Transactions.xml");
     //   Repository<Long, Transaction> transactionRepository = new TransactionFileSeverRepository(transactionValidator,"db/Transaction.csv");



        ClientFileSeverRepository clientFileSeverRepository = new ClientFileSeverRepository(clientValidator,"./db/Clients.csv");
        SongFileSeverRepository songFileSeverRepository = new SongFileSeverRepository(songValidator,"./db/Songs.csv");
        TransactionFileSeverRepository transactionFileSeverRepository = new TransactionFileSeverRepository(transactionValidator,"./db/Transaction.csv");

        SongService songService = new SongService(songFileSeverRepository);
        ClientService clientService = new ClientService(clientFileSeverRepository);
        TransactionService transactionService = new TransactionService(transactionFileSeverRepository);

        Console console = new Console(clientService, songService,transactionService);
        console.runMenu();

    }
}
