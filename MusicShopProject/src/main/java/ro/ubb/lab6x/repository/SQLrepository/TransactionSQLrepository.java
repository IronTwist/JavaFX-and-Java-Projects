package ro.ubb.lab6x.repository.SQLrepository;

import ro.ubb.lab6x.model.Client;
import ro.ubb.lab6x.model.Exceptions.ValidatorException;
import ro.ubb.lab6x.model.Song;
import ro.ubb.lab6x.model.Transaction;
import ro.ubb.lab6x.model.validators.Validator;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class TransactionSQLrepository  extends SQLrepository<Transaction> {
    public TransactionSQLrepository(String url, String user, String password, String tableName, Validator<Transaction> validator) {
        super(url, user, password, tableName, validator);
    }

    @Override
    protected Set<Transaction> getDataFromResultSet(ResultSet resultSet) throws SQLException {
        Set<Transaction> transactionsSet = new HashSet<>();
        while (resultSet.next()){
            Long id = resultSet.getLong("id");
            Long clientId = resultSet.getLong("client_id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String address = resultSet.getString("address");
            Client client = new Client(firstName,lastName,address);
            client.setId(clientId);
            Long songId = resultSet.getLong("song_id");
            String title = resultSet.getString("title");
            int yearOfRelease = resultSet.getInt("year_of_release");
            double price = resultSet.getDouble("price");
            Song song = new Song(title,yearOfRelease,price);
            song.setId(songId);
            Transaction transaction = new Transaction(client,song);
            transaction.setId(id);
            transactionsSet.add(transaction);
        }
        return  transactionsSet;
    }

    @Override
    protected Transaction getEntityfromResultSet(ResultSet resultSet) throws SQLException {
        resultSet.next();
        Long id = resultSet.getLong("id");
        Long clientId = resultSet.getLong("client_id");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        String address = resultSet.getString("address");
        Client client = new Client(firstName,lastName,address);
        client.setId(clientId);
        Long songId = resultSet.getLong("song_id");
        String title = resultSet.getString("title");
        int yearOfRelease = resultSet.getInt("year_of_release");
        double price = resultSet.getDouble("price");
        Song song = new Song(title,yearOfRelease,price);
        song.setId(songId);
        Transaction transaction = new Transaction(client,song);
        transaction.setId(id);
        return transaction;
    }

    @Override
    public Optional<Transaction> save(Transaction entity) throws ValidatorException {
        validator.validate(entity);
        String sql = "insert into tb_transactions(id_client,id_song) values (?,?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1,entity.getClient().getId());
            preparedStatement.setLong(2,entity.getSong().getId());
            preparedStatement.executeUpdate();
            return Optional.of(entity);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }

    @Override
    public Optional<Transaction> update(Transaction entity) throws ValidatorException {
        throw new RuntimeException("A transaction cannot be updated");
    }
}
