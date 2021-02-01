package GraphicInterface;

import Domain.Address;
import Domain.Client;
import Domain.DateTime;
import Service.ClientService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class UpdateClientGraphic {

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
    public Button btnUpdate;

    Client clientToUpdate;
    ClientService clientService;

    public void setServices(Client client, ClientService clientService) {
        System.out.println(client);
        this.clientToUpdate = client;
        this.clientService = clientService;
    }

    public void initialize(Client client) {
        txtName.setText(client.getName());
        txtLastName.setText(client.getSurname());
        txtUsername.setText(client.getUsername());
        txtPassword.setText(client.getPassword());
        txtCnp.setText(client.getCnp());
        txtSeriaId.setText(client.getIdSeries());
        txtNumberId.setText(String.valueOf(client.getIdNumber()));
        txtCounty.setText(client.getAddress().getCounty());
        txtCity.setText(client.getAddress().getCity());
        txtStreet.setText(client.getAddress().getStreet());
        txtStreetNumber.setText(String.valueOf(client.getAddress().getNumber()));
        txtOtherDetails.setText(client.getAddress().getOtherInfo());
    }


    public void handleUpdate(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("You are about to update this account");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

            Address newAddress = new Address(txtCounty.getText(),
                    txtCity.getText(),
                    txtStreet.getText(),
                    Integer.parseInt(txtStreetNumber.getText()),
                    txtOtherDetails.getText());

            Client updatedClient = new Client(
                    txtUsername.getText(),
                    txtPassword.getText(),
                    txtName.getText(),
                    txtLastName.getText(),
                    txtCnp.getText(),
                    txtSeriaId.getText(),
                    Integer.parseInt(txtNumberId.getText()),
                    newAddress,
                    new DateTime(),
                    clientToUpdate.getActiveClient()
            );
            updatedClient.setId(clientToUpdate.getId());

            clientService.updateClient(updatedClient);

            Stage stage = (Stage) btnUpdate.getScene().getWindow();
            stage.close();

        } else {
            Stage stage = (Stage) btnUpdate.getScene().getWindow();
            stage.close();
        }
    }

}
