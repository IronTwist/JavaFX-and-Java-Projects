package Repository;

import Domain.Entity;

import java.security.KeyException;
import java.util.List;

public interface IRepository<T extends Entity> {    //to make sure the type extends Entity

    void create(T entity);
    void create(T entity, int id);

    void update(T entity);

    List<T> read();

    T read(int idEntity);

    void delete(int idEntity);

}
