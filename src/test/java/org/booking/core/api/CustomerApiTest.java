package org.booking.core.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.booking.core.domain.entity.customer.Customer;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

class CustomerApiTest extends AbstractApiTest<Customer> {
    public static final String API_CUSTOMERS = "/api/customers/";
    public static Long createdId;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    @Order(1)
    @Test
    void post() {
        Customer business = generatedObject();
        String requestBody = getRequestBody(business);
        Response response = given()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post(API_CUSTOMERS)
                .then()
                .extract()
                .response();

        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        createdId = response.jsonPath().getLong("id");
        assertThat(response.jsonPath().getString("name")).isEqualTo(business.getName());
        assertThat(response.jsonPath().getString("email")).isEqualTo(business.getEmail());
    }

    @Order(2)
    @Test
    void get() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(API_CUSTOMERS + createdId)
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
        Customer business = generatedObject();
        String requestBody = getRequestBody(business);
        Response response = given()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .put(API_CUSTOMERS + createdId)
                .then()
                .extract()
                .response();
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("name")).isEqualTo(business.getName());
        assertThat(response.jsonPath().getString("email")).isEqualTo(business.getEmail());

    }

    @Order(4)
    @Test
    void getAll() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(API_CUSTOMERS)
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
                .delete(API_CUSTOMERS + createdId)
                .then()
                .extract()
                .response();

        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
    }

    @Override
    protected Customer generatedObject() {
        return Instancio.of(Customer.class)
                .ignore(field(Customer::getId))
                .ignore(field(Customer::getCreatedAt))
                .ignore(field(Customer::getModifiedAt))
                .create();
    }

}