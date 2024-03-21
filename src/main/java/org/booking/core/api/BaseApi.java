package org.booking.core.api;


import org.springframework.http.ResponseEntity;

public interface BaseApi<T, R, I> {

	ResponseEntity<R> create(T obj);

	ResponseEntity<R> update(I i, T obj);

	ResponseEntity<Boolean> delete(I i);

	ResponseEntity<R> getById(I i);

//    ResponseEntity<List<R>> getAll();

}
