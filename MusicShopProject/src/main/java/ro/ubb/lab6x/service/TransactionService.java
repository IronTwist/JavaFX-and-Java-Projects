package ro.ubb.lab6x.service;

import ro.ubb.lab6x.model.Client;
import ro.ubb.lab6x.model.Transaction;
import ro.ubb.lab6x.repository.Paging.PagingRepository;
import ro.ubb.lab6x.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TransactionService extends AbstractService<Transaction> {
    public TransactionService(PagingRepository<Long, Transaction> repository) {
        super(repository);
    }

    @Override
    public List<Transaction> myPersonalFilter(List<Transaction> list, Predicate<Transaction> predicate) {
        List<Transaction> returnList = new ArrayList<>();

        list.forEach(el -> {
            if(predicate.test(el)){
                returnList.add(el);
            }
        });

        return returnList;
    }
}
