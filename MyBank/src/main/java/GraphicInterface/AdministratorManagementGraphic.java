package GraphicInterface;

import Domain.Client;
import Service.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class AdministratorManagementGraphic {

    public Button txtGoToLogin;
    public Label labelUser;
    public Label labelUserName;
    public TableView tableAllClients;
    public Button btnApproveAccount;
    public Button btnDeleteAccount;
    public Button btnUpdateData;
    public Button btnUpdateClient;
    private int adminId;

    private AdministratorService administratorService;
    private ClientService clientService;
    private RonAccountService ronAccountService;
    private DepositService depositService;
    private TransactionService transactionService;

    @FXML UpdateClientGraphic updateClientGraphicController;

    private ObservableList<Client> clientObservableList = FXCollections.observableArrayList();
    
    public void setServices(int adminId, ClientService clientService, RonAccountService ronAccountService, DepositService depositService, TransactionService transactionService, AdministratorService administratorService) {
        this.adminId = adminId;
        this.clientService = clientService;
        this.administratorService = administratorService;
        this.ronAccountService = ronAccountService;
        this.depositService = depositService;
        this.transactionService = transactionService;
    }

    public void userWelcome(String username) {
        labelUser.setText("You are logged in as: " + username +" (id: " + adminId +" )");
        labelUserName.setText("Welcome back, " + administratorService.readAdmin(adminId).getSurname() + " " + administratorService.readAdmin(adminId).getName());

    }

    public void initialize(){
        Platform.runLater(() -> {
            clientObservableList.clear();

        for(Client c: clientService.readAllClients()){
            clientObservableList.add(c);
        }

        tableAllClients.setItems(clientObservableList);
        });
    }


    public void refresh() {
        clientObservableList.clear();

        for(Client c: clientService.readAllClients()){
            clientObservableList.add(c);
        }

        tableAllClients.setItems(clientObservableList);
    }

    public void handleApproveAccount(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoaderApprove = new FXMLLoader(getClass().getResource("/ApproveAccountGraphic.fxml"));
        Parent approvedAccountParent = fxmlLoaderApprove.load();

        ApproveAccountGraphic approveAccountGraphic = fxmlLoaderApprove.getController();
        approveAccountGraphic.setServices(clientService, ronAccountService);
        approveAccountGraphic.initialize();

        Stage newStage = new Stage();
        newStage.setWidth(220);
        newStage.setHeight(245);
        newStage.setTitle("Approve account");
        newStage.setScene(new Scene(approvedAccountParent));
        newStage.show();
    }

    public void handleDeleteAccount(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DeleteClientAccountGraphic.fxml"));
        Parent deleteParent = fxmlLoader.load();

        DeleteClientAccountGraphic deleteClientAccountGraphic = fxmlLoader.getController();
        deleteClientAccountGraphic.setServices(clientService, ronAccountService, depositService, transactionService);
        deleteClientAccountGraphic.initialize();

        Stage stage = new Stage();
        stage.setTitle("Delete client account");
        stage.setScene(new Scene(deleteParent));
        stage.show();
    }

    public void handleUpdateClient(ActionEvent actionEvent) throws IOException {
        ObservableList<Client> clientRowList =  tableAllClients.getSelectionModel().getSelectedItems();

        if(!clientRowList.isEmpty()) {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UpdateClientGraphic.fxml"));
            Parent updateClientParent = fxmlLoader.load();

            for (Client client : clientRowList) {
                UpdateClientGraphic updateClientGraphic = fxmlLoader.getController();
                updateClientGraphic.setServices(client, clientService);
                updateClientGraphic.initialize(client);
            }

            Stage stage = new Stage();
            stage.setTitle("Update client");
            stage.setScene(new Scene(updateClientParent));
            stage.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No row selected");
            alert.setContentText("You must select a row from client table!");
            alert.show();
        }
    }

    //************************************************************************************************************************

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
