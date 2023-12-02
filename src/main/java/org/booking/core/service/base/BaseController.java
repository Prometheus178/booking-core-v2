package org.booking.core.service.base;

import java.util.List;

public interface BaseController<T, I> {

    T create(T obj);

    T update(I i, T obj);

    boolean delete(I i);

    T getById(I i);

    List<T> getAllUsers();

}
