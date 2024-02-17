package org.booking.core.api.appointment;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.booking.core.domain.dto.*;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.util.LogActionType;
import org.booking.core.util.LoggerUtil;
import org.instancio.Instancio;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;
import static org.booking.core.api.AbstractApiTestAssured.BASE_URI;
import static org.booking.core.api.BusinessApiTestAssured.API_BUSINESSES;
import static org.booking.core.api.BusinessServiceApiTestAssured.API_BUSINESSES_SERVICES;
import static org.booking.core.api.EmployeeApiTestAssured.API_EMPLOYEES;
import static org.instancio.Select.field;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppointmentApiTestAssured {

    public static final String API_APPOINTMENTS = "/api/appointments/";
    public static Long createdIdBusinessService;
    public static Long createdIdBusinessDto;
    public static Long createdIdEmployeeDto;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    @Order(1)
    @Test
    void postBusinessDto() {
        BusinessDto businessDto = generatedObjectBusinessDto();
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

    @Order(2)
    @Test
    void postBusinessServiceDto() {
        BusinessServiceDto businessServiceDto = generatedObjectBusinessServiceDto();
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
        createdIdBusinessService = response.jsonPath().getLong("id");
        assertThat(response.jsonPath().getString("name")).isEqualTo(businessServiceDto.getName());
        assertThat(response.jsonPath().getDouble("price")).isEqualTo(businessServiceDto.getPrice(), withPrecision(2d));
        assertThat(response.jsonPath().getString("description")).isEqualTo(businessServiceDto.getDescription());
    }


    @Order(3)
    @Test
    void findAvailableTimeSlots() {
        Response response = given()
                .contentType(ContentType.JSON)
                .param("businessServiceId", createdIdBusinessService)
                .param("day", LocalDate.now().toEpochDay())
                .when()
                .get(API_APPOINTMENTS + "find/available-time-slots")
                .then()
                .extract()
                .response();
        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        List<Object> timeSlot = response.jsonPath().getList("TimeSlot");
        System.out.println(timeSlot);


    }

    void postEmployeeDto() {
        EmployeeDto business = generatedObjectEmployeeDto();
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
        createdIdEmployeeDto = response.jsonPath().getLong("id");
        assertThat(response.jsonPath().getString("name")).isEqualTo(business.getName());
        assertThat(response.jsonPath().getString("email")).isEqualTo(business.getEmail());
    }

    public ReservationDto generatedObjectReservationDto() {
        return Instancio.of(ReservationDto.class)
                .ignore(field(ReservationDto::getId))
                .create();
    }
    public BusinessDto generatedObjectBusinessDto() {
        return Instancio.of(BusinessDto.class)
                .ignore(field(BusinessDto::getId))
                .ignore(field(BusinessDto::getBusinessHours))
                .ignore(field(BusinessDto::getType))
                .ignore(field(BusinessDto::getReservationSchedule))
                .create();
    }

    public BusinessServiceDto generatedObjectBusinessServiceDto() {
        return Instancio.of(BusinessServiceDto.class)
                .ignore(field(BusinessServiceDto::getId))
                .create();
    }


    public EmployeeDto generatedObjectEmployeeDto() {
        return Instancio.of(EmployeeDto.class)
                .ignore(field(EmployeeDto::getId))
                .ignore(field(EmployeeDto::getReservationHistoryDto))
                .create();
    }

    protected String getRequestBody(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }
}
