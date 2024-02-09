package org.booking.core.mapper;

import org.booking.core.domain.entity.business.Business;
import org.instancio.Instancio;

public abstract class AbstractMapperTest<R>{

    protected R createObject(Class<?> clazz) {
        return (R) Instancio.of(clazz)
                .create();
    }
}
