package Repository;

import Domain.Entity;

import java.util.List;

public interface IRepository<T extends Entity> {

    void create(T entity);
    void create(T entity, int id);

    void update(T entity);

    T read(int idEntity);
    List<T> readAll();

    void delete(int idEntity);
}
