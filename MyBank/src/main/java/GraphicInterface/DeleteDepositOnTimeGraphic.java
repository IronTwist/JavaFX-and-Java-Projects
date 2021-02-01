package GraphicInterface;

import Domain.DateTime;
import Domain.Deposit;
import Domain.RonAccount;
import Service.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeleteDepositOnTimeGraphic {
    public TextField txtIdToDelete;
    public Button btnDeleteDeposit;
    private int clientId;
    private ClientService clientService;
    private RonAccountService ronAccountService;
    private DepositService depositService;
    private TransactionService transactionService;

    public ObservableList<Deposit> depositObservableList = FXCollections.observableArrayList();

    public void setServices(int clientId, ClientService clientService, RonAccountService ronAccountService, DepositService depositService, TransactionService transactionService) {
        this.clientId = clientId;
        this.clientService = clientService;
        this.ronAccountService = ronAccountService;
        this.depositService = depositService;
        this.transactionService = transactionService;
    }

    public void initialize() {

    }

    public void handleDeleteDeposit(ActionEvent actionEvent) {
        try {
            int idDelete = Integer.parseInt(txtIdToDelete.getText());
            double sumReturnedToAccount = 0;

            for (Deposit d : depositService.readAll()) {
                if (d.getIdClient() == clientId && idDelete == d.getId()) {
                    sumReturnedToAccount = d.getSold();
                }
            }

            for (RonAccount ronAcc : ronAccountService.readAllRonAccount()) {
                if (ronAcc.getClientId() == clientId) {
//
                    double newSold = ronAcc.getSold() + sumReturnedToAccount;
                    ronAcc.setSold(newSold);
                    ronAccountService.updateRonAccount(ronAcc);
                }
            }
            for (Deposit d : depositService.readAll()) {
                if (d.getId() == idDelete && clientId == d.getIdClient()) {
                    depositService.deleteDeposit(idDelete);
                    transactionService.addRonTransaction(clientId, new DateTime(), sumReturnedToAccount, "Credit");
                }
            }

            //close current stage
            Stage stage = (Stage) btnDeleteDeposit.getScene().getWindow();
            stage.close();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No id inserted or wrong id!");
            alert.show();
        }
    }
}
