package ro.ubb.lab6x.repository;


import ro.ubb.lab6x.model.Entity;
import ro.ubb.lab6x.model.Exceptions.ValidatorException;
import ro.ubb.lab6x.model.validators.Validator;
import ro.ubb.lab6x.repository.Paging.Page;
import ro.ubb.lab6x.repository.Paging.Pageable;
import ro.ubb.lab6x.repository.Paging.PagingRepository;
import ro.ubb.lab6x.repository.Repository;


import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author radu.
 */
public class InMemoryRepository<ID extends Serializable, T extends Entity<ID>> implements PagingRepository<ID, T> {

    private Map<ID, T> entities;
    private Validator<T> validator;

    public InMemoryRepository(Validator<T> validator) {
        this.validator = validator;
        entities = new HashMap<>();
    }

    @Override
    public Optional<T> findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<T> findAll() {
        Set<T> allEntities = entities.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
        return allEntities;
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<T> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        //TODO: pagination logic should be delegated


//        System.out.println("Sunt aici");
//        Stream<T> stream = (Stream<T>) new ArrayList<>(entities.entrySet());
//
//        ro.ubb.lab6x.repository.Paging.Impl.Pageable pageableProvided = (ro.ubb.lab6x.repository.Paging.Impl.Pageable) pageable;
//        ro.ubb.lab6x.repository.Paging.Impl.Page<T> returnPage = new ro.ubb.lab6x.repository.Paging.Impl.Page<T>(pageableProvided,stream);
//
//        Page<T> page = new ro.ubb.lab6x.repository.Paging.Impl.Page<T>(pageable,entities.entrySet().stream());
//
        return null;
        // throw new RuntimeException("not yet implemented");
    }


}
