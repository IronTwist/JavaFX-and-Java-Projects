package Service;

import Domain.*;
import Repository.IRepository;

import java.util.List;

public class AdministratorService {
    private IRepository<Administrator> administratorIRepository;
    private IRepository<Client> clientIRepository;
    private IRepository<Deposit> depositIRepository;
    private IRepository<RonAccount> ronAccountIRepository;
    private IRepository<Transaction> transactionIRepository;

    public AdministratorService(IRepository<Administrator> administratorIRepository, IRepository<Client> clientIRepository, IRepository<Deposit> depositIRepository, IRepository<RonAccount> ronAccountIRepository, IRepository<Transaction> transactionIRepository) {
        this.administratorIRepository = administratorIRepository;
        this.clientIRepository = clientIRepository;
        this.depositIRepository = depositIRepository;
        this.ronAccountIRepository = ronAccountIRepository;
        this.transactionIRepository = transactionIRepository;
    }

    public void addAdministrator(String name,
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
                                 String otherInfo
    ) {

        Administrator newAdministrator = new Administrator(username,
                password,
                name,
                surname,
                cnp,
                idSeries,
                idNumber, new Address(county, city, street, numberAddress, otherInfo),
                new DateTime());

        administratorIRepository.create(newAdministrator);
    }

    public void deleteAdministrator(int administratorId){
        administratorIRepository.delete(administratorId);
    }


    public List<Administrator> readAll() {
        return administratorIRepository.readAll();
    }

    public Administrator readAdmin(int adminId){
        return administratorIRepository.read(adminId);
    }
}
