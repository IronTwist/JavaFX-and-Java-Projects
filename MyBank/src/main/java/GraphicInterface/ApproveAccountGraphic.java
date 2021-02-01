package GraphicInterface;

import Domain.Client;
import Domain.RonAccount;
import Service.ClientService;
import Service.RonAccountService;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ApproveAccountGraphic {

    public TextField txtClientId;
    public ChoiceBox txtChoiceBox;
    public Button btnApprove;
    ClientService clientService;
    RonAccountService ronAccountService;
    
    public void setServices(ClientService clientService, RonAccountService ronAccountService) {
        this.clientService = clientService;
        this.ronAccountService = ronAccountService;
    }

    public void initialize() {
    }

    public void handleApprove(ActionEvent actionEvent) {
        try {

            int idClient = Integer.parseInt(txtClientId.getText());
            boolean choice = Boolean.parseBoolean(txtChoiceBox.getSelectionModel().getSelectedItem().toString());

            if(choice) {
                System.out.println(idClient);
                System.out.println(choice);

                for(Client c: clientService.readAllClients()){
                    if(c.getId() == idClient){
                        c.setActiveClient(true);
                        clientService.updateClient(c);
                    }
                }
                ronAccountService.addRonAccount(idClient, 0);


                Stage stage = (Stage) btnApprove.getScene().getWindow();
                stage.close();
            }else{
                for(Client c: clientService.readAllClients()){
                    if(c.getId() == idClient){
                        c.setActiveClient(false);
                        clientService.updateClient(c);
                    }
                }

                Stage stage = (Stage) btnApprove.getScene().getWindow();
                stage.close();
            }

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No id provided");
            alert.show();
        }
    }
}
