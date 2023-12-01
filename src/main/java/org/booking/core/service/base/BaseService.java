package org.booking.core.service.base;

import java.util.List;

public interface BaseService<T, ID> {

    T create(T obj);

    T update(ID id, T obj);

    boolean delete(ID id);

    T getUserById(ID id);

    List<T> getAllUsers();

}
