package GraphicInterface;

import Domain.DateTime;
import Domain.RonAccount;
import Service.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddDepositOnTimeGraphic {

    public ChoiceBox txtCheckBox;
    public Button btnAddDeposit;
    public TextField txtAmount;
    private int idClient;
    private ClientService clientService;
    private RonAccountService ronAccountService;
    private DepositService depositService;
    private TransactionService transactionService;

    public void setServices(int idClient,
            ClientService clientService,
            RonAccountService ronAccountService,
            DepositService depositService,
            TransactionService transactionService) {

        this.idClient = idClient;
        this.clientService = clientService;
        this.ronAccountService = ronAccountService;
        this.depositService = depositService;
        this.transactionService = transactionService;
    }

    public void initialize() {
    }


    public void handleAddNewTermDeposit(ActionEvent actionEvent) {
        double amountToWithdraw = Double.parseDouble(txtAmount.getText());
        double accountSum = 0;

        for(RonAccount ronAccount: ronAccountService.readAllRonAccount()){
            if(ronAccount.getClientId() == idClient){
                accountSum = ronAccount.getSold();
            }
        }

        if((accountSum - amountToWithdraw) < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Not enough money in account!");
            alert.show();
        }else {
            String choiceFromBox = txtCheckBox.getSelectionModel().getSelectedItem().toString();
            switch (choiceFromBox) {
                case "12 luni 12%":
                    timeMethodCalculate(idClient, 12, 360,amountToWithdraw);
                    break;
                case "3 luni 2.4%":
                    timeMethodCalculate(idClient, 2.4, 90,amountToWithdraw);
                    break;
                case "6 luni 4.5%":
                    timeMethodCalculate(idClient, 4.5, 180,amountToWithdraw);
                    break;
                case "1 luna 1.2%":
                    //1 luna 1.2%
                    timeMethodCalculate(idClient, 1.2, 31,amountToWithdraw);
                    break;
            }
        }//end else
    }

    public void transaction(int clientId,double amount){
        int depositSucces = 0;

        for(RonAccount ronAcc: ronAccountService.readAllRonAccount()){
            if(ronAcc.getClientId() == clientId){
//
                double newSold = ronAcc.getSold() - amount;
                if(newSold < 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Not enough money in account!");
                    alert.show();
                }else{
                    depositSucces = 1;

                    ronAcc.setSold(newSold);
                    ronAccountService.updateRonAccount(ronAcc);
                    transactionService.addRonTransaction(clientId,new DateTime(),amount,"Debit");
                }
            }
        }

        if(depositSucces == 1){
            Stage stage = (Stage) btnAddDeposit.getScene().getWindow();
            stage.close();
        }

    }//end transaction

    public void timeMethodCalculate(int idClient, double percent, double daysPerMonth, double amountToWithdraw){
        double finalAmount = 0;
        DateTime currentDate = new DateTime();
        LocalDate localDateTime = null;

        finalAmount = amountToWithdraw * (percent / 100) * (daysPerMonth / 360);
        int addDays = (int) daysPerMonth;

        localDateTime = currentDate.getDate().plusDays(addDays);
        currentDate.setDate(localDateTime);
        if(daysPerMonth == 31){
            depositService.addDeposit(idClient, amountToWithdraw, 1.2, 1, finalAmount, currentDate, new DateTime());
        }else if(daysPerMonth == 90){
            depositService.addDeposit(idClient, amountToWithdraw, 2.4, 3, finalAmount, currentDate, new DateTime());
        }else if(daysPerMonth == 180){
            depositService.addDeposit(idClient, amountToWithdraw, 4.5, 6, finalAmount, currentDate, new DateTime());
        }else if(daysPerMonth == 360){
            depositService.addDeposit(idClient, amountToWithdraw, 12, 12, finalAmount, currentDate, new DateTime());
        }

        transaction(idClient, amountToWithdraw);
    }
}

