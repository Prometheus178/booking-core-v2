package org.booking.core.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.booking.core.domain.entity.customer.Customer;
import org.booking.core.domain.entity.employee.Employee;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

class EmployeeApiTest extends AbstractApiTest<Employee> {
    public static final String API_EMPLOYEES = "/api/employees/";
    public static Long createdId;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Order(1)
    @Test
    void post() {
        Employee business = generatedObject();
        String requestBody = getRequestBody(business);
        Response response = given()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post(API_EMPLOYEES)
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
                .get(API_EMPLOYEES + createdId)
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
        Employee business = generatedObject();
        String requestBody = getRequestBody(business);
        Response response = given()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .put(API_EMPLOYEES + createdId)
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
                .get(API_EMPLOYEES)
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
                .delete(API_EMPLOYEES + createdId)
                .then()
                .extract()
                .response();

        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
    }

    @Override
    protected Employee generatedObject() {
        return Instancio.of(Employee.class)
                .ignore(field(Employee::getId))
                .ignore(field(Employee::getCreatedAt))
                .ignore(field(Employee::getModifiedAt))
                .create();
    }

}