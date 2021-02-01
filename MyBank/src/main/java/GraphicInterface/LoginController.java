package GraphicInterface;

import Domain.Administrator;
import Domain.Client;
import Service.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    public Button btnSignInClient;
    public TextField txtUsername;
    public PasswordField txtPassword;
    public Button btnNewClient;
    public Button btnSignInAdministrator;
    public TextField txtUsernameAdmin;
    public PasswordField txtPasswordAdmin;

    ClientService clientService;
    AdministratorService administratorService;
    RonAccountService ronAccountService;
    DepositService depositService;
    TransactionService transactionService;

    public void setServices(ClientService clientService,
                            RonAccountService ronAccountService,
                            DepositService depositService,
                            TransactionService transactionService,
                            AdministratorService administratorService) {

        this.clientService = clientService;
        this.administratorService = administratorService;
        this.ronAccountService = ronAccountService;
        this.depositService = depositService;
        this.transactionService = transactionService;
    }

    @FXML
    public void initialize() {
        Platform.runLater(()->{

        });
    }

    public void handleSignInClient(ActionEvent actionEvent) throws IOException {
        int clientId = -1;
        int foundUser = 0;
        String user = txtUsername.getText().toLowerCase();
        String password = txtPassword.getText().toLowerCase();

        for(Client client: clientService.readAllClients()) {
            if (client.getUsername().equals(user) && client.getPassword().equals(password)){
                foundUser = 1;
                if(client.isActiveClient()) {
                    clientId = client.getId();

                    FXMLLoader fxmlLoaderClient = new FXMLLoader(getClass().getResource("/ClientManagementGraphic.fxml"));
                    Parent clientParent = fxmlLoaderClient.load();

                    ClientManagementGraphic clientManagementGraphic = fxmlLoaderClient.getController();
                    clientManagementGraphic.setServices(clientId, clientService, ronAccountService, depositService, transactionService, administratorService);
                    clientManagementGraphic.userWelcome(client.getUsername());

                    //This line gets the stage information
                    Scene clientScene = new Scene(clientParent);
                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setWidth(1000);
                    window.setHeight(740);
                    window.setTitle("Client Bank Manager");
                    window.setResizable(false);
                    window.setScene(clientScene);
                    window.show();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Your account is not activated, wait for a moderator to approve it");
                    alert.show();
                }
            }
        }//end for

        if(foundUser == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Wrong username or password try again");
            alert.show();
        }
    }

    public void handleAddNewClient(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoaderAddClient = new FXMLLoader(getClass().getResource("/AddNewClientGraphic.fxml"));
        Parent addClientParent = fxmlLoaderAddClient.load();

        AddNewClientGraphic addNewClientGraphic = fxmlLoaderAddClient.getController();
        addNewClientGraphic.setServices(clientService, ronAccountService);
        addNewClientGraphic.initialize();

        Stage newStage = new Stage();
        newStage.setWidth(420);
        newStage.setHeight(640);
        newStage.setTitle("Welcome new client!");
        newStage.setScene(new Scene(addClientParent));
        newStage.show();
    }

    public void handleSignInAdministrator(ActionEvent actionEvent) throws IOException {
        int adminId = -1;
        int foundAdmin = 0;
        String user = txtUsernameAdmin.getText().toLowerCase();
        String password = txtPasswordAdmin.getText().toLowerCase();

        for(Administrator administrator: administratorService.readAll()) {
            if (administrator.getUsername().equals(user) && administrator.getPassword().equals(password)){
                foundAdmin = 1;

                adminId = administrator.getId();

                FXMLLoader fxmlLoaderAdministrator = new FXMLLoader(getClass().getResource("/AdministratorManagementGraphic.fxml"));
                Parent administratorParent = fxmlLoaderAdministrator.load();

                AdministratorManagementGraphic administratorManagementGraphic = fxmlLoaderAdministrator.getController();
                administratorManagementGraphic.setServices(adminId, clientService, ronAccountService, depositService, transactionService, administratorService);
                administratorManagementGraphic.userWelcome(administrator.getUsername());

                //This line gets the stage information
                Scene adminScene = new Scene(administratorParent);
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.setWidth(1000);
                window.setHeight(740);
                window.setTitle("Administrator Bank Manager");
                window.setResizable(false);
                window.setScene(adminScene);
                window.show();

            }
        }//end for

        if(foundAdmin == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Wrong username or password try again");
            alert.show();
        }

    }
}
