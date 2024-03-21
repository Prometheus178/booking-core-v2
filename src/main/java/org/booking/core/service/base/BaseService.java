package org.booking.core.service.base;

public interface BaseService<T, R, I> {

	R create(T request);

	R update(I id, T request);

	boolean delete(I id);

	R getById(I id);


}
