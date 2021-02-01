package Repository;

import Domain.Entity;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.max;

public class JSONFileRepository<T extends Entity> implements IRepository<T> {

    private String filename;
    private Gson gson;
    private Map<Integer,T> entities = new HashMap<>();
    private Type type;

    public JSONFileRepository(String filename, Type type){
        this.filename = filename;
        this.gson = new Gson();
        this.type = type;
    }

    private void readFile(){
        entities.clear();

        try(FileReader in = new FileReader(filename)){
            try(BufferedReader bufferedReader = new BufferedReader(in)){
                String line;

                while((line = bufferedReader.readLine()) != null){
                    T entity = gson.fromJson(line, type);
                    entities.put(entity.getId(), entity);
                }
            }
        }catch (Exception e){

        }
    }

    private void saveFile(){
        try(FileWriter out = new FileWriter(filename)){
            try(BufferedWriter bufferedWriter = new BufferedWriter(out)){
                for(T entity: entities.values()){
                    bufferedWriter.write(gson.toJson(entity));
                    bufferedWriter.newLine();
                }
            }

        }catch (Exception e){

        }
    }

    @Override
    public void create(T entity) {
        readFile();

        if(entities.size() == 0){
            entity.setId(0);
        }else{
            entity.setId(max(entities.keySet()) + 1);
        }

        entities.put(entity.getId(),entity);
        saveFile();
    }

    @Override
    public void create(T entity, int id) {
        readFile();
        entity.setId(id);
        entities.put(entity.getId(), entity);
        saveFile();
    }

    @Override
    public void update(T entity) {
        readFile();
        entities.put(entity.getId(), entity);
        saveFile();
    }

    @Override
    public T read(int idEntity) {
        readFile();
        return entities.get(idEntity);
    }

    @Override
    public List<T> readAll() {
        readFile();
        return new ArrayList<>(entities.values());
    }

    @Override
    public void delete(int idEntity) {
        readFile();
        entities.remove(idEntity);
        saveFile();
    }
}
