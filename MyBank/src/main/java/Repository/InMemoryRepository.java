package Repository;

import Domain.Entity;

import java.util.*;

import static java.util.Collections.max;

public class InMemoryRepository<T extends Entity> implements IRepository<T> {

    private Map<Integer, T> storage = new HashMap<>();

    @Override
    public void create(T entity) {
        if(storage.size() == 0){
            entity.setId(0);
        }else{
            entity.setId(max(storage.keySet()) + 1);
        }

        storage.put(entity.getId(), entity);
    }

    @Override
    public void create(T entity, int id) {
        entity.setId(id);
        storage.put(entity.getId(),entity);
    }

    @Override
    public void update(T entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public T read(int idEntity) {
        return storage.get(idEntity);
    }

    @Override
    public List<T> readAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void delete(int idEntity) {
        storage.remove(idEntity);
    }
}
