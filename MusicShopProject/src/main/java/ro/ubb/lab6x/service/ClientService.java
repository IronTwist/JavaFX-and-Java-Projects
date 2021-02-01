package ro.ubb.lab6x.service;

import ro.ubb.lab6x.model.Client;
import ro.ubb.lab6x.repository.Paging.PagingRepository;

import java.util.*;
import java.util.function.Predicate;

public class ClientService extends AbstractService<Client> {


    public ClientService(PagingRepository<Long, Client> repository) {
        super(repository);

    }

    public Optional<Client> updateClient(long id, String firstName, String lastName, String songId) {
        Client client = repository.findOne(id).get();
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setAdress(songId);
        return repository.update(client);
    }


    @Override
    public Iterable<Client> search(String searchString) {
        String searchStringToLowerCase = searchString.toLowerCase();

        Iterable<Client> clients = repository.findAll();
        List<Client> foundList = new ArrayList<>();

        clients.forEach(client -> {
            if(client.getFirstName().toLowerCase().contains(searchStringToLowerCase) ||
                    client.getLastName().toLowerCase().contains(searchStringToLowerCase)){
                foundList.add(client);
            }
        });

        return foundList;
    }

    @Override
    public List<Client> myPersonalFilter(List<Client> list, Predicate<Client> predicate) {
        List<Client> returnList = new ArrayList<>();

        list.forEach(el -> {
            if(predicate.test(el)){
                returnList.add(el);
            }
        });

        return returnList;
    }

    @Override
    public Set<Client> filterEntityByName(String s) {
        return super.filterEntityByName(s);
    }

    @Override
    public Set<Client> getNextEntitie() {

        return super.getNextEntitie();
    }
}
