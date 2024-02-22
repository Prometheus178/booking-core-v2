package org.booking.core.api;


public interface BaseApi<T, R, I> {

    R create(T obj);

    R update(I i, T obj);

    R delete(I i);

    R getById(I i);

    R getAllUsers();

}
