package ro.ubb.lab6x.service;

import ro.ubb.lab6x.model.Entity;
import ro.ubb.lab6x.repository.Paging.Impl.Pageable;
import ro.ubb.lab6x.repository.Paging.Page;
import ro.ubb.lab6x.repository.Paging.PagingRepository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractService<T extends Entity<Long>> {
    protected PagingRepository<Long, T> repository;

    private Pageable pageable;

    private int page = 0;
    private int size = 1;

    public AbstractService(PagingRepository<Long, T> repository) {
        this.repository = repository;
        this.pageable = new Pageable(page, size);
    }

    public Optional<T> add(T entity) {

        return repository.save(entity);
    }

    public Iterable<T> findAll() {
        return repository.findAll();
    }

    public Optional<T> findOne(long id) {
        return repository.findOne(id);
    }

    public Optional<T> delete(long id) {
        return repository.delete(id);
    }

    public Iterable<T> search(String searchString){

        return repository.findAll();
    }

    public List<T> myPersonalFilter(List<T> list, Predicate<T> predicate){
        return new ArrayList<>();
    }

    public Set<T> filterEntityByName(String s){ //filter
        Iterable<T> entities = repository.findAll();

        Set<T> filteredEntities = new HashSet<>();
        entities.forEach(filteredEntities::add);

        return filteredEntities;
    }

    public void setPageSize(int size){ this.size = size;}

    public Set<T> getNextEntitie(){ //"n"
        //TODO: use repository.findAll(Pageable)

//        Set<T> retElements = new HashSet<>();
//            for(T el: repository.findAll()){
//                retElements.add(el);
//            }

        Page<T> pageStream = repository.findAll(pageable);

        System.out.println(pageStream);

        Stream<T> setRet = pageStream.getContent();

        Set<T> setT = setRet.collect(Collectors.toSet());

        System.out.println(setT);

        return setT;

      //  return null;

    }



}
