package ro.ubb.lab6x.repository.SQLrepository;

import ro.ubb.lab6x.model.Entity;
import ro.ubb.lab6x.model.validators.Validator;
import ro.ubb.lab6x.repository.Repository;

import java.sql.*;
import java.util.*;

public abstract class SQLrepository<T extends Entity<Long>> implements Repository<Long, T> {
    protected final String url;
    protected final String user;
    protected final String password;
    protected final String tableName;
    protected final Validator<T> validator;


    public SQLrepository(String url, String user, String password, String tableName, Validator<T> validator) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.tableName = tableName;
        this.validator = validator;
    }


    @Override
    public Iterable<T> findAll() {
        StringBuilder stringBuilder = new StringBuilder("select * from ");
        String sql = stringBuilder.append(tableName).toString();
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return getDataFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }

    protected abstract Set<T> getDataFromResultSet(ResultSet resultSet) throws SQLException;

    @Override
    public Optional<T> findOne(Long aLong) {
        if (aLong == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        String sql = String.format("select * from %s where id=?", tableName);
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, aLong);
            ResultSet resultSet = preparedStatement.executeQuery();
            T entity = getEntityfromResultSet(resultSet);
            return Optional.ofNullable(entity);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    protected abstract T getEntityfromResultSet(ResultSet resultSet) throws SQLException;


    @Override
    public Optional<T> delete(Long aLong) {
        Optional <T> optional = findOne(aLong);
        String sql = String.format("delete from %s where id=?", tableName);
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, aLong);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return optional;
    }


}
