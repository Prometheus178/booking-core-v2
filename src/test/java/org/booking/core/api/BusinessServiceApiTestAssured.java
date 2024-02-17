package org.booking.core.api;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.booking.core.domain.dto.BusinessDto;
import org.booking.core.domain.dto.BusinessHoursDto;
import org.booking.core.domain.dto.BusinessServiceDto;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.util.LogActionType;
import org.booking.core.util.LoggerUtil;
import org.instancio.Instancio;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;
import static org.booking.core.api.BusinessApiTestAssured.API_BUSINESSES;
import static org.instancio.Select.field;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BusinessServiceApiTestAssured extends AbstractApiTestAssured<BusinessServiceDto> {

    public static final String API_BUSINESSES_SERVICES = "/api/business-services/";
    public static Long createdId;
    public static Long createdIdBusinessDto;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    @Order(0)
    @Test
    public void postBusinessDto() {
        BusinessDto businessDto = generatedBusinessDto();
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
        createdIdBusinessDto = response.jsonPath().getLong("id");
        LoggerUtil.logInfo(LogActionType.CREATE, Business.ENTITY_NAME, createdIdBusinessDto);

        assertThat(response.jsonPath().getString("name")).isEqualTo(businessDto.getName());
        assertThat(response.jsonPath().getString("address")).isEqualTo(businessDto.getAddress());
        assertThat(response.jsonPath().getString("description")).isEqualTo(businessDto.getDescription());
        assertThat(response.jsonPath().getString("type")).isEqualTo(businessDto.getType());
    }
    @Order(1)
    @Test
    public void post() {
        BusinessServiceDto businessServiceDto = generatedObject();
        businessServiceDto.setBusinessId(createdIdBusinessDto);
        String requestBody = getRequestBody(businessServiceDto);
        Response response = given()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post(API_BUSINESSES_SERVICES)
                .then()
                .extract()
                .response();

        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        createdId = response.jsonPath().getLong("id");
        assertThat(response.jsonPath().getString("name")).isEqualTo(businessServiceDto.getName());
        assertThat(response.jsonPath().getDouble("price")).isEqualTo(businessServiceDto.getPrice(), withPrecision(2d));
        assertThat(response.jsonPath().getString("description")).isEqualTo(businessServiceDto.getDescription());
    }

    @Order(2)
    @Test
    public void get() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(API_BUSINESSES_SERVICES + createdId)
                .then()
                .extract()
                .response();

        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getLong("id")).isEqualTo(createdId);

    }

    @Order(3)
    @Test
    public void update() {
        BusinessServiceDto businessServiceDto = generatedObject();
        businessServiceDto.setBusinessId(createdIdBusinessDto);
        String requestBody = getRequestBody(businessServiceDto);
        Response response = given()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .put(API_BUSINESSES_SERVICES + createdId)
                .then()
                .extract()
                .response();
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("name")).isEqualTo(businessServiceDto.getName());

        assertThat(response.jsonPath().getDouble("price")).isCloseTo(businessServiceDto.getPrice(),
                Assertions.within(delta));
        assertThat(response.jsonPath().getString("description")).isEqualTo(businessServiceDto.getDescription());
    }

    @Order(4)
    @Test
    public void getAll() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(API_BUSINESSES_SERVICES)
                .then()
                .extract()
                .response();
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
//        List<Long> idList = response.jsonPath().getList("id");
    }

    @Order(5)
    @Test
    public void delete() {
        assertThat(createdId).isNotNull();
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .delete(API_BUSINESSES_SERVICES + createdId)
                .then()
                .extract()
                .response();

        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
    }

    @Order(6)
    @Test
    public void deleteBusinessDto() {
        assertThat(createdIdBusinessDto).isNotNull();
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
    public BusinessServiceDto generatedObject() {
        return Instancio.of(BusinessServiceDto.class)
                .ignore(field(BusinessServiceDto::getId))
                .create();
    }


    public BusinessDto generatedBusinessDto() {
        return Instancio.of(BusinessDto.class)
                .ignore(field(BusinessDto::getId))
                .ignore(field(BusinessDto::getBusinessHours))
                .ignore(field(BusinessDto::getType))
                .ignore(field(BusinessDto::getReservationSchedule))
                .create();
    }

}