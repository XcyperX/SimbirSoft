package com.SimbirSoft.SimbirSoft.base;

import java.util.List;

public interface MapperService<E, D> {
    E toEntity(D dto);
    D toDto(E entity);

    List<E> toEntities(List<D> dto);
    List<D> toDtos(List<E> entity);
}
