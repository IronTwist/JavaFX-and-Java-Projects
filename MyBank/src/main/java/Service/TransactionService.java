package Service;

import Domain.DateTime;
import Domain.RonAccount;
import Domain.Transaction;
import Repository.IRepository;

import java.util.List;

public class TransactionService {
    private IRepository<Transaction> transactionIRepository;
    private IRepository<RonAccount> ronAccountIRepository;

    public TransactionService(IRepository<Transaction> transactionIRepository, IRepository<RonAccount> ronAccountIRepository) {
        this.transactionIRepository = transactionIRepository;
        this.ronAccountIRepository = ronAccountIRepository;
    }

    public void addRonTransaction(int clientId, DateTime timestamp, double amountTransaction, String type){
        Transaction newTransaction = new Transaction(clientId,timestamp,amountTransaction, type);
        transactionIRepository.create(newTransaction);
    }

    public void deleteTransaction(int id){
        transactionIRepository.delete(id);
    }

    public void updateTransaction(Transaction newTransaction){
        transactionIRepository.update(newTransaction);
    }

    public List<Transaction> readAllTransactions(){
        return transactionIRepository.readAll();
    }
    public Transaction readTransaction(int id){
        return transactionIRepository.read(id);
    }
}
