package org.booking.core.api;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.booking.core.domain.dto.BusinessServiceDto;
import org.booking.core.domain.entity.business.service.BusinessService;
import org.instancio.Instancio;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;
import static org.booking.core.api.BusinessApiTestAssured.API_BUSINESSES;
import static org.instancio.Select.field;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BusinessServiceApiTestAssured extends AbstractApiTestAssured<BusinessServiceDto> {

    public static final String API_BUSINESSES_SERVICES = "/api/business-services/";
    public static Long createdId;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    @Order(1)
    @Test
    public void post() {
        Long anyBusinessId = getAnyBusinessId();
        BusinessServiceDto businessServiceDto = generatedObject();
        businessServiceDto.setBusinessId(anyBusinessId);
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
        BusinessService business = getBusinessServices();
        String requestBody = getRequestBody(business);
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
        assertThat(response.jsonPath().getString("name")).isEqualTo(business.getName());
        assertThat(response.jsonPath().getDouble("price")).isEqualTo(business.getPrice().doubleValue());
        assertThat(response.jsonPath().getString("description")).isEqualTo(business.getDescription());
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


    private BusinessService getBusinessServices() {
        return Instancio.of(BusinessService.class)
                .ignore(field(BusinessService::getId))
                .ignore(field(BusinessService::getCreatedAt))
                .ignore(field(BusinessService::getModifiedAt))
                .create();
    }

    @Override
    public BusinessServiceDto generatedObject() {
        return Instancio.of(BusinessServiceDto.class)
                .ignore(field(BusinessServiceDto::getId))
                .create();
    }

    private Long getAnyBusinessId() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(API_BUSINESSES)
                .then()
                .extract()
                .response();
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        List<Integer> idList = response.jsonPath().getList("id");
        return idList.get(0).longValue();
    }

}