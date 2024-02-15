package org.booking.core.api.appointment;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.booking.core.api.AbstractApiTestAssured;
import org.booking.core.domain.dto.ReservationDto;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.booking.core.api.BusinessServiceApiTestAssured.API_BUSINESSES_SERVICES;
import static org.instancio.Select.field;

public class AppointmentApiTestAssured extends AbstractApiTestAssured<ReservationDto> {

    public static final String API_APPOINTMENTS = "/api/appointments/";
    public static Long createdId;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    // @Test
    @Override
    public void get() {
        Response businessServiceResponse = given()
                .contentType(ContentType.JSON)
                .when()
                .get(API_BUSINESSES_SERVICES)
                .then()
                .extract()
                .response();

        assertThat(businessServiceResponse.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        List<Object> ids = businessServiceResponse.body().jsonPath().getList("id");
        System.out.println(ids);
//        Response response = given()
//                .contentType(ContentType.JSON)
//              //  .param("businessServiceId", )
//                .param("day", LocalDate.now().toEpochDay())
//                .when()
//                .get(API_APPOINTMENTS + "find/available-time-slots")
//                .then()
//                .extract()
//                .response();
//
//        assertThat(response.statusCode())
//                .isEqualTo(HttpStatus.OK.value());
    }

    @Override
    public void post() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void getAll() {

    }

    @Override
    public ReservationDto generatedObject() {
        return Instancio.of(ReservationDto.class)
                .ignore(field(ReservationDto::getId))
                .create();
    }
}
