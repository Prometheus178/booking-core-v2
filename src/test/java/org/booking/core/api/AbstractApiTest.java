package org.booking.core.api;

import com.google.gson.Gson;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class AbstractApiTest<T> {
    public static final String BASE_URI = "http://localhost:8080";

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
