package Service;

import Domain.DateTime;
import Domain.RonAccount;
import Repository.IRepository;

import java.util.List;

public class RonAccountService {
    private IRepository<RonAccount> ronAccountIRepository;

    public RonAccountService(IRepository<RonAccount> ronAccountIRepository) {
        this.ronAccountIRepository = ronAccountIRepository;
    }

    public void addRonAccount(int clientId, double sold){
        RonAccount ronAccount = new RonAccount(clientId,
                sold,
                new DateTime());

        ronAccountIRepository.create(ronAccount);
    }

    public void deleteRonAccount(int id){
        ronAccountIRepository.delete(id);
    }

    public void updateRonAccount(RonAccount ronAccount){
        ronAccountIRepository.update(ronAccount);
    }

    public List<RonAccount> readAllRonAccount(){
        return ronAccountIRepository.readAll();
    }

    public RonAccount readRonAccount(int id){
        return  ronAccountIRepository.read(id);
    }
}
