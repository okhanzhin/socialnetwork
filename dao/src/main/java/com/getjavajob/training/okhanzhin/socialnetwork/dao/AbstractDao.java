package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import java.util.List;

public abstract class AbstractDao<T> {

    public abstract T create(T t);

    public abstract T getById(int id);

    public abstract void update(T t);

    public abstract void delete(T t);

    public abstract List<T> getAll();

    
}
