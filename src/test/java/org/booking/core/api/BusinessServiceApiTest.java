package org.booking.core.api;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.booking.core.domain.entity.business.service.BusinessService;
import org.instancio.Instancio;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BusinessServiceApiTest extends AbstractApiTest<BusinessService>{

    public static final String API_BUSINESSES_SERVICES = "/api/business-services/";
    public static Long createdId;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    @Order(1)
    @Test
    void post() {
        BusinessService business = generatedObject();
        String requestBody = getRequestBody(business);
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
        assertThat(response.jsonPath().getString("name")).isEqualTo(business.getName());
        assertThat(response.jsonPath().getDouble("price")).isEqualTo(business.getPrice().doubleValue());
        assertThat(response.jsonPath().getString("description")).isEqualTo(business.getDescription());
    }

    @Order(2)
    @Test
    void get() {
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
    void update() {
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
    void getAll() {
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
    void delete() {
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
    protected BusinessService generatedObject() {
        return Instancio.of(BusinessService.class)
                .ignore(field(BusinessService::getId))
                .ignore(field(BusinessService::getCreatedAt))
                .ignore(field(BusinessService::getModifiedAt))
                .create();
    }
}