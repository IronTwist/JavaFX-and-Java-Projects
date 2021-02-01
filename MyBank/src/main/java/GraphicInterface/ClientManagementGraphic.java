package GraphicInterface;

import Domain.Deposit;
import Domain.RonAccount;
import Domain.Transaction;
import Service.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientManagementGraphic {
    public Button txtGoToLogin;
    public Label labelUser;
    public int clientId;
    public Label labelSold;
    public Button btnDeposit;
    public Button btnUpdate;
    public TableView tableTransaction;
    public Button btnWithdraw;
    public Label labelUserName;
    public Button btnAddDepositOnTime;
    public TableView tableDeposit;
    public Button btnDeleteDeposit;

    private ClientService clientService;
    private AdministratorService administratorService;
    private RonAccountService ronAccountService;
    private DepositService depositService;
    private TransactionService transactionService;

    private ObservableList<Transaction> transactionObservableList = FXCollections.observableArrayList();
    private ObservableList<Deposit> depositObservableList = FXCollections.observableArrayList();

    public void setServices(int clientId,
                            ClientService clientService,
                            RonAccountService ronAccountService,
                            DepositService depositService,
                            TransactionService transactionService,
                            AdministratorService administratorService) {
        this.clientId = clientId;
        this.clientService = clientService;
        this.administratorService = administratorService;
        this.ronAccountService = ronAccountService;
        this.depositService = depositService;
        this.transactionService = transactionService;
    }

    public void userWelcome(String username){
        labelUser.setText("You are logged in as: " + username +" (id: " + clientId +" )");
        labelUserName.setText("Welcome back, " + clientService.readClient(clientId).getSurname() + " " + clientService.readClient(clientId).getName());
        for(RonAccount ronAccount: ronAccountService.readAllRonAccount()){
            int clientIdFound = ronAccount.getClientId();
            if(clientIdFound == clientId){
                String sold = String.valueOf(ronAccount.getSold());

                    labelSold.setText("Sold: " + sold + " ron");

            }
        }

    }

    public void initialize() throws IOException {
            Platform.runLater(() -> {
                refresh();
            });
    }

    public void refresh() {
        transactionObservableList.clear();
        depositObservableList.clear();

        for(Transaction t: transactionService.readAllTransactions()){
            if(clientId == t.getClientId()){
                transactionObservableList.add(t);
            }
        }

        for(Deposit d: depositService.readAll()){
            if(clientId == d.getIdClient()){
                depositObservableList.add(d);
            }
        }

        tableTransaction.setItems(transactionObservableList);
        tableDeposit.setItems(depositObservableList);

        for(RonAccount ronAccount: ronAccountService.readAllRonAccount()){
            int clientIdFound = ronAccount.getClientId();
            if(clientIdFound == clientId){
                String sold = String.valueOf(ronAccount.getSold());

                labelSold.setText("Sold: " + sold + " ron");

            }
        }
    }

    public void refreshData() {
        refresh();
    }

    public void handleAddDepositOnTime(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoaderDepositOnTime = new FXMLLoader(getClass().getResource("/AddDepositOnTimeGraphic.fxml"));
        Parent AddDepositParent = fxmlLoaderDepositOnTime.load();

        AddDepositOnTimeGraphic addDepositOnTimeGraphic = fxmlLoaderDepositOnTime.getController();
        addDepositOnTimeGraphic.setServices(clientId,clientService,ronAccountService,depositService,transactionService);
        addDepositOnTimeGraphic.initialize();

        Stage newStage = new Stage();
        newStage.setWidth(280);
        newStage.setHeight(260);
        newStage.setTitle("Add a deposit on time");
        newStage.setScene(new Scene(AddDepositParent));
        newStage.show();
    }


    public void handleDeleteDeposit(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoaderDeleteDeposit = new FXMLLoader(getClass().getResource("/DeleteDepositOnTimeGraphic.fxml"));
        Parent DeleteDepositParent = fxmlLoaderDeleteDeposit.load();

        DeleteDepositOnTimeGraphic deleteDepositOnTimeGraphic = fxmlLoaderDeleteDeposit.getController();
        deleteDepositOnTimeGraphic.setServices(clientId, clientService, ronAccountService,depositService,transactionService);
        deleteDepositOnTimeGraphic.initialize();

        Stage newStage = new Stage();
        newStage.setWidth(390);
        newStage.setHeight(168);
        newStage.setTitle("Add a deposit on time");
        newStage.setScene(new Scene(DeleteDepositParent));
        newStage.show();


    }

    public void handleDeposit(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DepositGraphic.fxml"));
        Parent depositParent = fxmlLoader.load();

        DepositGraphic depositGraphic = fxmlLoader.getController();
        depositGraphic.setServices(clientId, ronAccountService,transactionService);
        depositGraphic.initialize();

        Stage newStage = new Stage();
        newStage.setWidth(470);
        newStage.setHeight(260);
        newStage.setTitle("Deposit money");
        newStage.setScene(new Scene(depositParent));
        newStage.show();
    }


    public void handleWithdraw(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/WithdrawGraphic.fxml"));
        Parent depositParent = fxmlLoader.load();

        WithdrawGraphic withdrawGraphic = fxmlLoader.getController();
        withdrawGraphic.setServices(clientId, ronAccountService,transactionService);
        withdrawGraphic.initialize();

        Stage newStage = new Stage();
        newStage.setWidth(470);
        newStage.setHeight(260);
        newStage.setTitle("Withdraw money");
        newStage.setScene(new Scene(depositParent));
        newStage.show();
    }

    public void handleGenerateReport(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GenerateClientReport.fxml"));
        Parent reportParent = fxmlLoader.load();

        GenerateClientReport generateClientReport = fxmlLoader.getController();
        generateClientReport.setServices(clientService.readClient(clientId), clientService, ronAccountService, transactionService);
        generateClientReport.initialize();

        Stage newStage = new Stage();
        newStage.setTitle("Generate Client Report");
        newStage.setScene(new Scene(reportParent));
        newStage.show();
    }

    /**********************************************************************************************************************************************************
     * Here is LOG OUT to manage another account*
     */
    public void signOut(ActionEvent actionEvent) throws IOException {
        //Go to login
        FXMLLoader fxmlLoaderLogin = new FXMLLoader(getClass().getResource("/LoginMenu.fxml"));
        Parent clientParent = fxmlLoaderLogin.load();

        LoginController loginController = fxmlLoaderLogin.getController();
        loginController.setServices(clientService, ronAccountService, depositService,transactionService, administratorService);
        loginController.initialize();

        Scene clientScene = new Scene(clientParent);
        clientScene.widthProperty().add(401);
        clientScene.heightProperty().add(200);

        //This line gets the stage information
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setWidth(401);
        window.setHeight(240);
        window.setScene(clientScene);
        window.show();
    }


    public void handleExitApp(ActionEvent actionEvent) {
        Platform.exit();
    }
}
