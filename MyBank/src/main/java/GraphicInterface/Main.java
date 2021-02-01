package GraphicInterface;

import Domain.*;
import Repository.IRepository;
import Repository.JSONFileRepository;
import Service.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoaderLogin= new FXMLLoader(getClass().getResource("/LoginMenu.fxml"));
        Parent rootLogin = fxmlLoaderLogin.load();

        LoginController controller = fxmlLoaderLogin.getController();

        IRepository<Administrator> administratorIRepository = new JSONFileRepository<>("admin.dat", Administrator.class);
        IRepository<Client> clientIRepository= new JSONFileRepository<>("clients.dat", Client.class);
        IRepository<Deposit> depositIRepository= new JSONFileRepository<>("deposits.dat", Deposit.class);
        IRepository<RonAccount> ronAccountIRepository= new JSONFileRepository<>("ronAccounts.dat", RonAccount.class);
        IRepository<Transaction> transactionIRepository= new JSONFileRepository<>("transactions.dat", Transaction.class);


        ClientService clientService = new ClientService(clientIRepository);
        RonAccountService ronAccountService = new RonAccountService(ronAccountIRepository);

        DepositService depositService = new DepositService(depositIRepository);
        TransactionService transactionService = new TransactionService(transactionIRepository, ronAccountIRepository);

        AdministratorService administratorService = new AdministratorService(administratorIRepository,
                clientIRepository,
                depositIRepository,
                ronAccountIRepository,
                transactionIRepository);


        controller.setServices(clientService, ronAccountService, depositService,transactionService, administratorService);
        controller.initialize();

        Scene scene = new Scene(rootLogin);

        primaryStage.setWidth(401);
        primaryStage.setHeight(240);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);

    }
}
