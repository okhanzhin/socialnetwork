package com.getjavajob.training.okhanzhin.socialnetwork.dao.spring;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AbstractRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface AbstractSpringRepository<T, ID> extends AbstractRepository<T, ID>, JpaRepository<T, ID> {

    default T create(T entity) {
        return save(entity);
    }

    default T update(T entity) {
        return save(entity);
    }

    default T getById(ID id) {
        Optional<T> optional = findById(id);

        return optional.orElse(null);
    }

    default List<T> getAll() {
        return findAll();
    }

    default long getCountOfSearchResults(String filter) {
        return 0;
    }

    default List<T> searchEntitiesPagination(String filter, int currentPage) {
        return null;
    }

    default List<T> searchAllEntities(String filter) {
        return null;
    }
}
