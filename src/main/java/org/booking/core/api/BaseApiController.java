package org.booking.core.api;


public interface BaseApiController<T, R, I> {

    R create(T obj);

    R update(I i, T obj);

    R delete(I i);

    R getById(I i);

    R getAllUsers();

}
