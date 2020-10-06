package com.getjavajob.training.okhanzhin.socialnetwork.dao;

public abstract class AbstractDao<T> {

    public abstract T create(T t);

    public abstract T getById(long id);

    public abstract void update(T t);

    public abstract void delete(T t);
}
