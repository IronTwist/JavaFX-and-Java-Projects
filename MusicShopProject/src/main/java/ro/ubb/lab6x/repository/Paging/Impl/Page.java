package ro.ubb.lab6x.repository.Paging.Impl;


import ro.ubb.lab6x.model.Client;
import ro.ubb.lab6x.repository.Paging.Pageable;

import java.util.stream.Stream;

public class Page implements ro.ubb.lab6x.repository.Paging.Page<Client> {
    

    @Override
    public Pageable getPageable() {
        return null;
    }

    @Override
    public Pageable nextPageable() {
        return null;
    }

    @Override
    public Stream getContent() {
        return null;
    }
}
