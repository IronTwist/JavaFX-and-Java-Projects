package Repository;

import Domain.Entity;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.max;

public class InMemoryRepository<T extends Entity> implements IRepository<T> {   //generic repository to store data

    private Map<Integer,T> storage = new HashMap<>();
    private int currentId;

    @Override
    public void create(T entity){
        if(storage.size() == 0){
            entity.setId(0);
        }else {
            entity.setId(max(storage.keySet()) + 1);
        }

        storage.put(entity.getId(), entity);
    }

    @Override
    public void create(T entity, int id){

        entity.setId(id);

        storage.put(entity.getId(), entity);
    }

    @Override
    public void update(T entity){

        storage.put(entity.getId(), entity);
    }

    @Override
    public List<T> read() {
        List<T> resultList = new ArrayList<>();
        for(T entity: storage.values()){
            resultList.add(entity);
        }

        return resultList;
    }

    @Override
    public T read(int idEntity) {
        return storage.get(idEntity);
    }

    @Override
    public void delete(int idEntity) {

        this.storage.remove(idEntity);
    }
}
