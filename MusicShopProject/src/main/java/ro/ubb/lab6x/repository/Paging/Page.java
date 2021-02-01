package ro.ubb.lab6x.repository.Paging;

import java.util.stream.Stream;

/**
 * author: radu
 */
public interface Page<T> {
    ro.ubb.lab6x.repository.Paging.Pageable getPageable();

    Pageable nextPageable();

    Stream<T> getContent();

}
