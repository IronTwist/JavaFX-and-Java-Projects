package ro.ubb.lab6x.repository.SQLrepository;

import ro.ubb.lab6x.model.Client;
import ro.ubb.lab6x.model.Exceptions.ValidatorException;
import ro.ubb.lab6x.model.validators.Validator;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ClientSQLrepository extends SQLrepository<Client> {
    public ClientSQLrepository(String url, String user, String password, String tableName, Validator<Client> validator) {
        super(url, user, password, tableName, validator);
    }

    @Override
    protected Set<Client> getDataFromResultSet(ResultSet resultSet) throws SQLException {
        Set<Client> clientSet = new HashSet<>();

        while(resultSet.next()){
            Long id = resultSet.getLong("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String address = resultSet.getString("address");

            Client client = new Client(firstName, lastName, address);
            client.setId(id);

            clientSet.add(client);
        }
        return clientSet;
    }

    @Override
    protected Client getEntityfromResultSet(ResultSet resultSet) throws SQLException {
        resultSet.next();
        Long id = resultSet.getLong("id");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        String address = resultSet.getString("address");

        Client client = new Client(firstName, lastName, address);
        client.setId(id);
        return client;
    }

    @Override
    public Optional<Client> save(Client entity) throws ValidatorException {
        validator.validate(entity);
        String sql = "insert into tb_clients(firstName, lastName, address) values (?,?,?)";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getAdress());
            preparedStatement.executeUpdate();
            return Optional.of(entity);

        }catch (SQLException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Client> update(Client entity) throws ValidatorException {
        validator.validate(entity);
        String sql = "update tb_clients set firstName=?, lastName=?, address=? where id=?";

        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1, entity.getFirstName());
                preparedStatement.setString(2, entity.getLastName());
                preparedStatement.setString(3, entity.getAdress());
                preparedStatement.setLong(4, entity.getId());
                preparedStatement.executeUpdate();

                return Optional.of(entity);
        }catch (SQLException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
