package GraphicInterface;

import Service.AdministratorService;
import Service.ClientService;
import Service.RonAccountService;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddNewClientGraphic {
    public TextField txtName;
    public TextField txtLastName;
    public TextField txtUsername;
    public PasswordField txtPassword;
    public TextField txtCnp;
    public TextField txtSeriaId;
    public TextField txtNumberId;
    public TextField txtCounty;
    public TextField txtCity;
    public TextField txtStreet;
    public TextField txtStreetNumber;
    public TextArea txtOtherDetails;
    public Button btnSubmit;

    ClientService clientService;
    RonAccountService ronAccountService;

    public void setServices(ClientService clientService, RonAccountService ronAccountService) {
        this.clientService = clientService;
        this.ronAccountService = ronAccountService;
    }

    public void initialize() {
    }

    public void handleSubmit(ActionEvent actionEvent) {
//        try{
            //TODO validation


            clientService.addClient(
                    txtName.getText(),
                    txtLastName.getText(),
                    txtUsername.getText(),
                    txtPassword.getText(),
                    txtCnp.getText(),
                    txtSeriaId.getText(),
                    Integer.parseInt(txtNumberId.getText()),
                    txtCounty.getText(),
                    txtCity.getText(),
                    txtStreet.getText(),
                    Integer.parseInt(txtStreetNumber.getText()),
                    txtOtherDetails.getText()

            );

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Welcome");
        alert.setContentText("Your account is created, wait for administrator to approve it, Have a nice day!");
        alert.show();

        Stage stage = (Stage) btnSubmit.getScene().getWindow();
        stage.close();

//        }catch (Exception e){
//
//        }

    }
}
