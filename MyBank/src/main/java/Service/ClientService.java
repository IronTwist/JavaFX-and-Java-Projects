package Service;

import Domain.*;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private IRepository<Client> clientIRepository;

    public ClientService(IRepository<Client> clientIRepository) {
        this.clientIRepository = clientIRepository;
    }

    public void addClient(String name,
                          String surname,
                          String username,
                          String password,
                          String cnp,
                          String idSeries,
                          int idNumber,
                          String county,
                          String city,
                          String street,
                          int numberAddress,
                          String otherInfo){

        Client newClient =  new Client(username,
                password,
                name,
                surname,
                cnp,
                idSeries,idNumber,
                new Address(county,city,street, numberAddress,otherInfo),
                new DateTime(),
                false);

        clientIRepository.create(newClient);

    }// end addClient

    public void updateClient(Client client){
        clientIRepository.update(client);
    }

    public void deleteClient(int idClient){
        clientIRepository.delete(idClient);
    }

    public Client readClient(int idClient){
       return clientIRepository.read(idClient);
    }

    public List<Client> readAllClients(){
       return clientIRepository.readAll();
    }

    public List<Client> searchForClient(String searchword) {
        int found = 0;
        String searchWord = searchword.toLowerCase();
        List<Client> returnList = new ArrayList<>();

        for (Client c : clientIRepository.readAll()) {
            String name = c.getName().toLowerCase();
            String surname = c.getSurname().toLowerCase();
            String user = c.getUsername().toLowerCase();
            String city = c.getAddress().getCity().toLowerCase();
            String street = c.getAddress().getStreet().toLowerCase();

            if (name.contains(searchWord) || surname.contains(searchWord) || user.contains(searchWord) || city.contains(searchWord) || street.contains(searchWord)) {
                found = 1;
                returnList.add(c);
            }
        }

        if (found == 0) {
            System.out.println("No client found");
        }

        return returnList;
    }//end searchForClient


}
