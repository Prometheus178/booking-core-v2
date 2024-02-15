package org.booking.core.api;

import com.google.gson.Gson;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class AbstractApiTestAssured<T> {
    public static final String BASE_URI = "http://localhost:8080";

    public abstract void post();

    public abstract void get();

    public abstract void update();

    public abstract void delete();

    public abstract void getAll();

    public abstract T generatedObject();


    protected String getRequestBody(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }


}
