package com.getjavajob.training.okhanzhin.socialnetwork.dao.hibernate;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AbstractRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public abstract class AbstractHibernateRepository<T, ID> implements AbstractRepository<T, ID> {
    private static final int RECORD_PER_PAGE = 8;

    @PersistenceContext
    protected EntityManager entityManager;
    private Class<T> entityClass;

    public AbstractHibernateRepository() {
    }

    protected void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract List<String> makePaths();

    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public T update(T entity) {
        entityManager.merge(entity);
        return entity;
    }

    public T getById(ID id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> getAll() {
        return entityManager.createQuery("FROM " + entityClass.getName(), entityClass).getResultList();
    }

    public void deleteById(ID id) {
        T entity = getById(id);
        entityManager.remove(entity);
    }

    public long getCountOfSearchResults(String filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> root = criteriaQuery.from(entityClass);
        Predicate predicate = getPredicate(filter, criteriaBuilder, root);
        criteriaQuery.select(criteriaBuilder.count(root));
        criteriaQuery.where(predicate);

        Long resultsCount = entityManager.createQuery(criteriaQuery).getSingleResult();

        return resultsCount != null ? resultsCount : 0;
    }

    private Predicate getPredicate(String filter, CriteriaBuilder criteriaBuilder, Root<T> root) {
        List<String> paths = makePaths();
        List<Predicate> predicates = new ArrayList<>();

        for (String pathString : paths) {
            Expression<String> path = criteriaBuilder.lower(root.get(pathString));
            String valueParam = "%" + filter + "%";
            Predicate predicate = criteriaBuilder.like(path, valueParam.toLowerCase());
            predicates.add(predicate);
        }

        Predicate finalPredicate;

        if (predicates.size() > 1) {
            finalPredicate = criteriaBuilder.or(predicates.get(0), predicates.get(1));
        } else {
            finalPredicate = predicates.get(0);
        }

        return finalPredicate;
    }

    public List<T> searchEntitiesPagination(String filter, int currentPage) {
        CriteriaQuery<T> criteriaQuery = prepareSearchCriteriaQuery(filter);

        int firstRecord = currentPage * RECORD_PER_PAGE - RECORD_PER_PAGE;
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery).
                setFirstResult(firstRecord).setMaxResults(RECORD_PER_PAGE);

        return query.getResultList();
    }

    public List<T> searchAllEntities(String filter) {
        CriteriaQuery<T> criteriaQuery = prepareSearchCriteriaQuery(filter);
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }

    private CriteriaQuery<T> prepareSearchCriteriaQuery(String value) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        Predicate predicate = getPredicate(value, criteriaBuilder, root);
        criteriaQuery.where(predicate).distinct(true);

        return criteriaQuery;
    }
}