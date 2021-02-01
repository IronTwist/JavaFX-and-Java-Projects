package ro.ubb.lab6x.repository.SQLrepository;

import ro.ubb.lab6x.model.Exceptions.ValidatorException;
import ro.ubb.lab6x.model.Song;
import ro.ubb.lab6x.model.validators.Validator;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SongSQLrepository extends SQLrepository<Song> {


    public SongSQLrepository(String url, String user, String password, String tableName, Validator<Song> validator) {
        super(url, user, password, tableName, validator);
    }


    @Override
    public Optional<Song> save(Song entity) throws ValidatorException {
        validator.validate(entity);

        String sql = "insert into tb_songs(title, year_of_release, price) values (?,?,?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setInt(2, entity.getYearOfRelease());
            preparedStatement.setDouble(3, entity.getPrice());
            preparedStatement.executeUpdate();
            return Optional.of(entity);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }


    }

    @Override
    protected Set<Song> getDataFromResultSet(ResultSet resultSet) throws SQLException {
        Set<Song> songSet = new HashSet<>();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            int yearOfRelease = resultSet.getInt("year_of_release");
            double price = resultSet.getDouble("price");
            Song song = new Song(title, yearOfRelease, price);
            song.setId(id);
            songSet.add(song);
        }
        return songSet;
    }

    @Override
    protected Song getEntityfromResultSet(ResultSet resultSet) throws SQLException {
        resultSet.next();
        Long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        int yearOfRelease = resultSet.getInt("year_of_release");
        double price = resultSet.getDouble("price");
        Song song = new Song(title, yearOfRelease, price);
        song.setId(id);
        return song;
    }

    @Override
    public Optional<Song> update(Song entity) throws ValidatorException {
        validator.validate(entity);
        String sql = "update tb_songs set title=?, year_of_release=?, price=? where id=?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setInt(2, entity.getYearOfRelease());
            preparedStatement.setDouble(3, entity.getPrice());
            preparedStatement.setLong(4,entity.getId());
            preparedStatement.executeUpdate();
            return Optional.of(entity);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
