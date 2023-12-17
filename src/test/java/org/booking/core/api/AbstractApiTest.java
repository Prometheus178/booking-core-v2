package org.booking.core.api;

import com.google.gson.Gson;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class AbstractApiTest<T> {

    abstract void post();

    abstract void get();

    abstract void update();

    abstract void delete();

    abstract void getAll();

    protected abstract T generatedObject();


    protected String getRequestBody(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }


}
