package Service;

import Domain.DateTime;
import Domain.Deposit;
import Repository.IRepository;

import java.util.ArrayList;
import java.util.List;

public class DepositService {
    private IRepository<Deposit> depositIRepository;

    public DepositService(IRepository<Deposit> depositIRepository) {
        this.depositIRepository = depositIRepository;
    }

    public void addDeposit(int idClient,
            double sold,
            double percent,
            int periodMonths,
            double soldFinalDeposit,
            DateTime timestamp,
            DateTime timestampDepositCreated){

        Deposit newDeposit = new Deposit(idClient,sold, percent, periodMonths, soldFinalDeposit, timestamp, timestampDepositCreated);
        depositIRepository.create(newDeposit);
    }

    public void deleteDeposit(int id){
        depositIRepository.delete(id);
    }

    public void updateDeposit(Deposit newDeposit){
        depositIRepository.update(newDeposit);
    }

    public List<Deposit> readAll(){
        return depositIRepository.readAll();
    }

    public Deposit read(int id){
        return depositIRepository.read(id);
    }
}
