package com.SimbirSoft.SimbirSoft.base;

import java.util.List;

public interface CRUDService<T, ID> {

    T getById(ID id);

    T save(T t);

    T update(T t);

    void delete(ID id);

    List<T> findAll();
}