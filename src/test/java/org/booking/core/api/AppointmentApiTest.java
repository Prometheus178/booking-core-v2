package org.booking.core.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.booking.core.domain.entity.reservation.Reservation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.booking.core.api.BusinessServiceApiTest.API_BUSINESSES_SERVICES;

public class AppointmentApiTest extends AbstractApiTest<Reservation> {

    public static final String API_APPOINTMENTS = "/api/appointments/";
    public static Long createdId;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    @Override
    void get() {
        Response businessServiceResponse = given()
                .contentType(ContentType.JSON)
                .when()
                .get(API_BUSINESSES_SERVICES)
                .then()
                .extract()
                .response();
        assertThat(businessServiceResponse.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        businessServiceResponse.body();

        Response response = given()
                .contentType(ContentType.JSON)
              //  .param("businessServiceId", )
                .param("day", LocalDate.now().toEpochDay())
                .when()
                .get(API_APPOINTMENTS + "find/available-time-slots")
                .then()
                .extract()
                .response();

        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
    }

    @Override
    void post() {

    }

    @Override
    void update() {

    }

    @Override
    void delete() {

    }

    @Override
    void getAll() {

    }

    @Override
    protected Reservation generatedObject() {
        return null;
    }
}
