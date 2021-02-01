package ro.ubb.lab6x.UI;

import ro.ubb.lab6x.model.*;
import ro.ubb.lab6x.model.Exceptions.ValidatorException;
import ro.ubb.lab6x.service.ClientService;
import ro.ubb.lab6x.service.SongService;
import ro.ubb.lab6x.service.TransactionService;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Console {
    private final ClientService clientService;
    private final SongService songService;
    private final TransactionService transactionService;

    public Console(ClientService clientService, SongService songService, TransactionService transactionService) {
        this.clientService = clientService;
        this.songService = songService;
        this.transactionService = transactionService;
    }


    public void runMenu() {
        char option = printMenu();
        while (option != 'x') {
            switch (option) {
                case '1':
                    handleAddClient();
                    break;
                case '2':
                    handleDeleteClient();
                    break;
                case '3':
                    handleUpdateClient();
                    break;
                case '4':
                    handleAddSong();
                    break;
                case '5':
                    handleDeleteSong();
                    break;
                case '6':
                    handleUpdateSong();
                    break;
                case '7':
                    handleShowAllClients();
                    break;
                case '8':
                    handleShowAllSongs();
                    break;
                case '9':
                    handleShowAllTransactions();
                    break;
                case 'a':
                    handleAddTransaction();
                    break;
                case 's':
                    handleSearch();
                    break;
                case 'f':
                    handleFilter();
                    break;
                default: System.out.println("Invalid option");
            }
            option = printMenu();
        }
        System.out.println("Program closed");
    }

    private void handleFilter() {
        String option = readString(
                "\n1. Show transactions where song price are greater than a given price, sorted by transaction id" +
                "\n2. Show transactions ordered by clients lastname" +
                "\n3. Show the name of clients who bought a song" +
                "\n4. Show all artists name from songs in sorted order" +
                "\n5. Show clients for every song" +
                "\n6: Client who spend more money on songs" +
                "\nEnter Option: ");

        List<Transaction> transactions = new ArrayList<>();
        for (Transaction t : transactionService.findAll()) {
            transactions.add(t);
        }

        switch (option) {
            case "1":
                String price = readString("Price greater or equal with: ");

                transactions.stream()
                        .filter(t -> t.getSong().getPrice() >= Double.parseDouble(price))
                        .sorted((t1, t2) -> t1.getId().compareTo(t2.getId()))
                        .forEach(t -> System.out.println("\n" + t.getId() + ". " + t));

                break;
            case "2":
                Comparator<Transaction> comparLastName = (t1, t2) -> t1.getClient().getLastName().compareToIgnoreCase(t2.getClient().getLastName());

                transactions.stream()
                        .sorted(comparLastName)
                        .forEach(t -> System.out.println("\n" + t.getId() + ". " + t));

                break;
            case "3":
                Long id = Long.parseLong(readString("Enter song id: "));
                Predicate<Transaction> nameClientBySongBuy = t -> t.getSong().getId().equals(id);

                List<Transaction> filtered = transactionService.myPersonalFilter(transactions, nameClientBySongBuy);
                filtered.forEach(t -> System.out.println(t.getClient().getFirstName() + " " + t.getClient().getLastName()));

                break;
            case "4":

                Function<Song, String> artistName = s -> s.getTitle()
                        .substring(0, s.getTitle()
                        .indexOf("-"));
                Set<String> artistSetName = new TreeSet<>();

                songService.findAll()
                        .forEach(s -> artistSetName.add(artistName.apply(s)));

                artistSetName.forEach(System.out::println);

                break;
            case "5":
                System.out.println("Show clients for every song: \n");

                Map<String, List<Transaction>> groupBySongs = transactions.stream()
                        .collect(Collectors.groupingBy(p -> p.getSong().getTitle()));

                for(Map.Entry<String, List<Transaction>> foundElem: groupBySongs.entrySet()){
                        List<Transaction> tfound = foundElem.getValue().stream()
                                                    .distinct()
                                                    .collect(Collectors.toList());

                        System.out.println(foundElem.getKey() + " { ");
                        tfound.stream()
                                .map(e -> e.getClient().getFirstName() + " " + e.getClient().getLastName())
                                .forEach(e -> System.out.print(e + ","));
                        System.out.println("\n } \n");
                }

                break;

            case "6": //Clientul care a cumparat cele mai multe cantece

                Map<Client, Integer> myListMap = new HashMap<>();

                    for(Client c: clientService.findAll()) {

                        int count =0;

                        count += transactions.stream()
                                .filter(t -> c.getId().equals(t.getClient().getId())).count();

                        myListMap.put(c, count);
                    }

                    int max = 0;

                max = getMax(myListMap, max);

                for(Map.Entry<Client, Integer> el: myListMap.entrySet()){
                        if(el.getValue() == max) {
                            System.out.println(el.getKey().getFirstName() + " with " + el.getValue() + " songs");
                        }
                }

                break;
            default:
                System.out.println("Wrong option");
                break;
        }


}

    private int getMax(Map<Client, Integer> myListMap, int max) {
        for(Map.Entry<Client, Integer> el: myListMap.entrySet()){
            if(el.getValue() > max){
                max = el.getValue();
            }
        }
        return max;
    }


    private void handleSearch() {
        String s = readString("1. Search client \n2. Search song \n Option: ");

        if(s.equals("1")){

            String clientStringSearch = readString("Give the string to search in client: ");

            List<Client> foundList = (List<Client>) clientService.search(clientStringSearch);

            foundList.sort(Comparator.comparing(Client::getFirstName));
            System.out.println("\nClients ar in sorted order by firstName:\n" +
                    "----------------------------------------\n" +foundList);

        }else if(s.equals("2")){
            String songStringSearch = readString("Give the string to search in songs: ");

            System.out.println(songService.search(songStringSearch));
        }else{
            System.out.println("Wrong option!");
        }

    }

    private void handleAddTransaction() {
        try {
            //long transactionId = Long.parseLong(readString("Enter transaction id: "));
            long songId = Long.parseLong(readString("Enter song id: "));
            long clientId = Long.parseLong(readString("Enter client id: "));

            AtomicReference<Song> songAtomicReference = new AtomicReference<>();
            AtomicReference<Client> clientAtomicReference = new AtomicReference<>();
            songService.findOne(songId).ifPresent(songAtomicReference::set);
            clientService.findOne(clientId).ifPresent(clientAtomicReference::set);
            Transaction transaction = new Transaction(clientAtomicReference.get(),songAtomicReference.get());
           // transaction.setId(transactionId);
            transactionService.add(transaction);
            System.out.println("Transaction added "+ transaction);
        }catch (Exception e){
            e.printStackTrace();
            handleAddTransaction();
        }
    }

    private void handleShowAllTransactions() {
      //  System.out.println(transactionService.findAll().toString());
//        for(Transaction t: transactionService.findAll()){
//            System.out.println(t.getId() + ". " + t.getClient() + " " + t.getSong());
//        }
        List<Transaction> transactions = new ArrayList<>();
        transactionService.findAll().forEach(transactions::add);

        transactions.stream()
                .sorted(Comparator.comparing(Entity::getId))
                .forEach(t -> System.out.println(t.getId() + ". " + t.getClient() + " " + t.getSong()));

    }

    private void handleShowAllSongs() {
        System.out.println(songService.findAll().toString());
    }

    private void handleShowAllClients() {
      //  System.out.println(clientService.findAll().toString());

        int size = Integer.parseInt(readString("Enter page size: "));
        clientService.setPageSize(size);

        System.out.println("enter 'n' - for next; 'x' - for exit: ");

        while(true){
            String cmd = readString("Enter option: ");
            if(cmd.equals("x")){
                System.out.println("exit");
                break;
            }
            if(!cmd.equals("n")){
                System.out.println("this option is not yet implemented");
                continue;
            }

            Set<Client> clients = clientService.getNextEntitie();
            if(clients.size() == 0){
                System.out.println("No more clients");
                break;
            }

            clients.forEach(System.out::println);
        }

    }

    private void handleUpdateSong() {
        try {
            long id = Long.parseLong(readString("Enter id: "));
            String title = readString("Enter song Title: ");
            int yearOfRelease = Integer.parseInt(readString("Enter release year: "));
            double price = Double.parseDouble(readString("Enter the song price: "));

            System.out.println("Song updated "+songService.updateSong(id, title, yearOfRelease, price).get());
        } catch (Exception e) {
            e.printStackTrace();
            handleUpdateSong();
        }

    }

    private void handleDeleteSong() {
        try {
            long id = Long.parseLong(readString("Enter id: "));
            System.out.println("song deleted -" + songService.delete(id));
        } catch (Exception e) {
            e.printStackTrace();
            handleDeleteSong();
        }

    }

    private void handleAddSong() {
        try {

            //long id = Long.parseLong(readString("Enter id: "));
            String title = readString("Enter song Title: ");
            int yearOfRelease = Integer.parseInt(readString("Enter release year: "));
            double price = Double.parseDouble(readString("Enter the song price: "));
            Song song = new Song(title, yearOfRelease, price);
            //song.setId(id);
            songService.add(song);
            System.out.println("New song added " + song);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was an error");
            handleAddSong();
        }
    }

    private void handleUpdateClient() {
        try {
            long id = Long.parseLong(readString("Enter id: "));
            String firstName = readString("Enter first name: ");
            String lastName = readString("Enter last name: ");
            String adress = readString("Enter adress: ");
            clientService.updateClient(id, firstName, lastName, adress);
        } catch (Exception e) {
            e.printStackTrace();
            handleUpdateClient();
        }

    }

    private void handleDeleteClient() {
        try {
            long id = Long.parseLong(readString("Enter id: "));
            clientService.findOne(id).ifPresentOrElse(client -> {
                 System.out.println("Client deleted: " + clientService.delete(id).get());
                },()->{
                throw new ValidatorException("Client not found");
            });


        } catch (Exception e) {
            e.printStackTrace();
            handleDeleteClient();
        }
    }

    private void handleAddClient() {
        try {
           // long id = Long.parseLong(readString("Enter id: "));
            String firstName = readString("Enter first name: ");
            String lastName = readString("Enter last name: ");
            String address = readString("Enter address: ");
            Client client = new Client(firstName, lastName, address);
           // client.setId(id);
            clientService.add(client);

            System.out.println("New Client added " + client);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was an error");
            handleAddClient();
        }
    }

    private char printMenu() {
        System.out.println("\n--------Menu-------\n" +
                "1 - Add client\n" +
                "2 - Delete client\n" +
                "3 - Update client\n\n" +
                "4 - Add song\n" +
                "5 - Delete song\n" +
                "6 - Update song\n\n" +
                "7 - Show all clients\n" +
                "8 - Show all songs\n" +
                "9 - Show all transactions\n\n" +
                "a - Add new transactions\n" +
                "s - Search database\n" +
                "f - Filter database\n" +
                "x - Exit");
        return readString("Choose an option: ").charAt(0);
    }

    private String readString(String prompter) {
        System.out.print(prompter);
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

}
