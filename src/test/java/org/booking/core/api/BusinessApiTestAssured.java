package org.booking.core.api;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.booking.core.domain.dto.BusinessDto;
import org.booking.core.domain.dto.BusinessHoursDto;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.util.LogActionType;
import org.booking.core.util.LoggerUtil;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

public class BusinessApiTestAssured extends AbstractApiTestAssured<BusinessDto> {

    public static final String API_BUSINESSES = "/api/businesses/";
    public static Long createdId;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    @Order(1)
    @Test
    public void post() {
        BusinessDto businessDto = generatedObject();
        BusinessHoursDto businessHoursDto = new BusinessHoursDto();
        businessHoursDto.setOpenTime(LocalTime.of(10, 0).toString());
        businessHoursDto.setCloseTime(LocalTime.of(18, 0).toString());
        businessDto.setBusinessHours(businessHoursDto);
        businessDto.setType("BARBERSHOP");

        String requestBody = getRequestBody(businessDto);
        Response response = given()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post(API_BUSINESSES)
                .then()
                .extract()
                .response();

        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        createdId = response.jsonPath().getLong("id");
        LoggerUtil.logInfo(LogActionType.CREATE, Business.ENTITY_NAME, createdId);

        assertThat(response.jsonPath().getString("name")).isEqualTo(businessDto.getName());
        assertThat(response.jsonPath().getString("address")).isEqualTo(businessDto.getAddress());
        assertThat(response.jsonPath().getString("description")).isEqualTo(businessDto.getDescription());
        assertThat(response.jsonPath().getString("type")).isEqualTo(businessDto.getType());
    }

    @Order(2)
    @Test
    public void get() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(API_BUSINESSES + createdId)
                .then()
                .extract()
                .response();

        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getLong("id")).isEqualTo(createdId);
        LoggerUtil.logInfo(LogActionType.READ, Business.ENTITY_NAME, createdId);


    }

    @Order(3)
    @Test
    public
    void update() {
        BusinessDto businessDto = generatedObject();
        String requestBody = getRequestBody(businessDto);
        Response response = given()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .put(API_BUSINESSES + createdId)
                .then()
                .extract()
                .response();
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        LoggerUtil.logInfo(LogActionType.UPDATE, Business.ENTITY_NAME, createdId);
        assertThat(response.jsonPath().getString("name")).isEqualTo(businessDto.getName());
        assertThat(response.jsonPath().getString("address")).isEqualTo(businessDto.getAddress());
        assertThat(response.jsonPath().getString("description")).isEqualTo(businessDto.getDescription());
        assertThat(response.jsonPath().getString("type")).isEqualTo(businessDto.getType());
    }

    @Order(4)
    @Test
    public
    void getAll() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(API_BUSINESSES)
                .then()
                .extract()
                .response();
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
    }

    @Order(5)
    @Test
    public
    void delete() {
        assertThat(createdId).isNotNull();
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .delete(API_BUSINESSES + createdId)
                .then()
                .extract()
                .response();

        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        LoggerUtil.logInfo(LogActionType.DELETE, Business.ENTITY_NAME, createdId);

    }

    @Override

    public BusinessDto generatedObject() {
        return Instancio.of(BusinessDto.class)
                .ignore(field(BusinessDto::getId))
                .ignore(field(BusinessDto::getBusinessHours))
                .ignore(field(BusinessDto::getType))
                .ignore(field(BusinessDto::getReservationSchedule))
                .create();
    }

}