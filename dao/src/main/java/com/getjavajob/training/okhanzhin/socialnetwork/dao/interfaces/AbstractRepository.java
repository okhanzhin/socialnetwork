package com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces;

import java.util.List;

public interface AbstractRepository<T, ID> {

    T create(T entity);

    T update(T entity);

    T getById(ID id);

    List<T> getAll();

    void deleteById(ID id);

    long getCountOfSearchResults(String filter);

    List<T> searchEntitiesPagination(String filter, int currentPage);

    List<T> searchAllEntities(String filter);
}
