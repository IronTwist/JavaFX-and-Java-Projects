package GraphicInterface;

import Domain.Client;
import Domain.Deposit;
import Domain.RonAccount;
import Domain.Transaction;
import Service.ClientService;
import Service.DepositService;
import Service.RonAccountService;
import Service.TransactionService;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class DeleteClientAccountGraphic {

    public TextField txtIdToDelete;
    public Button btnDeleteAccount;
    private ClientService clientService;
    private RonAccountService ronAccountService;
    private DepositService depositService;
    private TransactionService transactionService;

    public void setServices(ClientService clientService,
            RonAccountService ronAccountService,
            DepositService depositService,
            TransactionService transactionService) {
        this.clientService = clientService;
        this.ronAccountService = ronAccountService;
        this.depositService = depositService;
        this.transactionService = transactionService;
    }

    public void initialize() {

    }

    public void handleDeleteAccountClient(ActionEvent actionEvent) {

        try{
            int idClientToDelete = Integer.parseInt(txtIdToDelete.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("You are about to delete this account");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){

                for(Client c: clientService.readAllClients()){
                    if(c.getId() == idClientToDelete){
                        clientService.deleteClient(c.getId());
                    }
                }

                for (RonAccount r : ronAccountService.readAllRonAccount()) {
                    if (r.getClientId() == idClientToDelete) {
                        ronAccountService.deleteRonAccount(r.getId());
                    }
                }

                for (Deposit deposit : depositService.readAll()) {
                    if (deposit.getIdClient() == idClientToDelete) {
                        depositService.deleteDeposit(deposit.getId());
                    }
                }

                for (Transaction tr : transactionService.readAllTransactions()) {
                    if (tr.getClientId() == idClientToDelete) {
                        transactionService.deleteTransaction(tr.getId());
                    }
                }

                Stage stage = (Stage) btnDeleteAccount.getScene().getWindow();
                stage.close();

            } else {
                Stage stage = (Stage) btnDeleteAccount.getScene().getWindow();
                stage.close();
            }


        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No id provided!");
            alert.show();

        }

    }


}
