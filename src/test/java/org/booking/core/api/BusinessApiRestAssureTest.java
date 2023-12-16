package org.booking.core.api;


import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.booking.core.domain.entity.business.Business;
import org.instancio.Instancio;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BusinessApiRestAssureTest {

    public static final String API_BUSINESSES = "/api/businesses/";
    public static Long createdId;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Order(1)
    @Test
    void create() {
        Business business = getBusiness();
        String requestBody = getRequestBody(business);
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
        assertThat(response.jsonPath().getString("name")).isEqualTo(business.getName());
        assertThat(response.jsonPath().getString("address")).isEqualTo(business.getAddress());
        assertThat(response.jsonPath().getString("description")).isEqualTo(business.getDescription());
        assertThat(response.jsonPath().getString("type")).isEqualTo(business.getType().name());
    }

    @Order(2)
    @Test
    void get() {
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

    }

    @Order(3)
    @Test
    void update() {
        Business business = getBusiness();
        String requestBody = getRequestBody(business);
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
        assertThat(response.jsonPath().getString("name")).isEqualTo(business.getName());
        assertThat(response.jsonPath().getString("address")).isEqualTo(business.getAddress());
        assertThat(response.jsonPath().getString("description")).isEqualTo(business.getDescription());
        assertThat(response.jsonPath().getString("type")).isEqualTo(business.getType().name());
    }

    @Order(4)
    @Test
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
//        List<Long> idList = response.jsonPath().getList("id");
    }

    @Order(5)
    @Test
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
    }

    private String getRequestBody(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    private Business getBusiness() {
        return Instancio.of(Business.class)
                .ignore(field(Business::getId))
                .ignore(field(Business::getCreatedAt))
                .ignore(field(Business::getModifiedAt))
                .create();
    }

}