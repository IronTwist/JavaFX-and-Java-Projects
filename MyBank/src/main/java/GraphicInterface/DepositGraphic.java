package GraphicInterface;

import Domain.DateTime;
import Domain.RonAccount;
import Service.RonAccountService;
import Service.TransactionService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class DepositGraphic {
    public Button btnDeposit;
    public Button btnCancel;
    public TextField txtAmount;

    public int clientId;
    RonAccountService ronAccountService;
    TransactionService transactionService;

    public void setServices(int clientId, RonAccountService ronAccountService, TransactionService transactionService) {
        this.clientId = clientId;
        this.ronAccountService = ronAccountService;
        this.transactionService = transactionService;
    }

    public void initialize() {
    }

    public void handleDeposit(ActionEvent actionEvent) throws IOException {
        double amount = Double.parseDouble(txtAmount.getText());
        int id = -1;
        int depositSucces = 0;

        for(RonAccount ronAcc: ronAccountService.readAllRonAccount()){
            if(ronAcc.getClientId() == clientId){
//
               double newSold = ronAcc.getSold() + amount;
               depositSucces = 1;
               ronAcc.setSold(newSold);
               ronAccountService.updateRonAccount(ronAcc);
            }
        }

        if(depositSucces == 1){
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        }

        transactionService.addRonTransaction(clientId,new DateTime(),amount,"Credit");
    }

    public void handleCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
