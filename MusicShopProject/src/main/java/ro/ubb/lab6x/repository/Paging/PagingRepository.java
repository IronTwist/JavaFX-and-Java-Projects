package ro.ubb.lab6x.repository.Paging;

import ro.ubb.lab6x.model.Entity;
import ro.ubb.lab6x.repository.Repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author: radu
 */
public interface PagingRepository<ID extends Serializable,
        T extends Entity<ID>>
        extends Repository<ID, T> {

    Page<T> findAll(Pageable pageable);

    //TODO: any other methods are allowed...


}
