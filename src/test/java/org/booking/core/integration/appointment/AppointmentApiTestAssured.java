package org.booking.core.integration.appointment;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.booking.core.domain.entity.reservation.TimeSlot;
import org.booking.core.domain.request.*;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.booking.core.integration.AbstractIntegrationTest.BASE_URI;
import static org.booking.core.integration.customer.EmployeeIntegrationTest.API_CUSTOMERS;
import static org.instancio.Select.field;

@WebMvcTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppointmentApiTestAssured {

    public static final String API_APPOINTMENTS = "/api/v1/customers/appointments";
    public static Long createdIdBusinessService;
    public static Long createdIdBusinessDto;
    public static Long createdIdEmployeeDto;
    public static Long createdIdCustomerDto;
    public static Long createdIdReservationDto;
    public static List<TimeSlot> timeSlots;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    @Order(4)
        //@Test
    void postCustomerDto() {
        CustomerRequest customerRequest = generatedObjectCustomerDto();
        String requestBody = getRequestBody(customerRequest);
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
        createdIdCustomerDto = response.jsonPath().getLong("id");
        assertThat(response.jsonPath().getString("name")).isEqualTo(customerRequest.getName());
        assertThat(response.jsonPath().getString("email")).isEqualTo(customerRequest.getEmail());
    }

    @Order(5)
        //@Test
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
        timeSlots = response.jsonPath().getList(".", TimeSlot.class);
        assertThat(timeSlots).isNotEmpty();
    }

    @Order(6)
        //@Test
    void reservation() {
        ReservationRequest dto = createReservation(0);
        String requestBody = getRequestBody(dto);
        Response response = given()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post(API_APPOINTMENTS)
                .then()
                .extract()
                .response();

        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        createdIdReservationDto = response.jsonPath().getLong("id");
    }

    @Order(7)
        //@Test
    void modifyReservation() {
        ReservationRequest dto = createReservation(1);
        String requestBody = getRequestBody(dto);
        Response response = given()
                .contentType(ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .put(API_APPOINTMENTS  + createdIdReservationDto)
                .then()
                .extract()
                .response();

        assertThat(response.statusCode())
                .isEqualTo(HttpStatus.OK.value());
        createdIdReservationDto = response.jsonPath().getLong("id");
    }

    private ReservationRequest createReservation(int i) {
        ReservationRequest dto = new ReservationRequest();
        dto.setEmployeeId(createdIdEmployeeDto);
        dto.setServiceId(createdIdBusinessService);
        TimeSlot timeSlot = timeSlots.get(i);
        LocalTime startTime = timeSlot.getStartTime();
        LocalTime endTime = timeSlot.getEndTime();

        LocalDate currentDay = LocalDate.now();
        LocalDateTime reservedTime = LocalDateTime.of(currentDay.getYear(), currentDay.getMonth(), currentDay.getDayOfMonth(),
                startTime.getHour(), startTime.getMinute());
        dto.setBookingTime(reservedTime.toString());
        DurationRequest durationRequest = new DurationRequest();
        durationRequest.setStartTime(reservedTime.toString());
        LocalDateTime endDateTime = LocalDateTime.of(currentDay.getYear(), currentDay.getMonth(),
                currentDay.getDayOfMonth(),
                endTime.getHour(), endTime.getMinute());
        durationRequest.setEndTime(endDateTime.toString());
        dto.setDuration(durationRequest);
        return dto;
    }


    public BusinessRequest generatedObjectBusinessDto() {
        return Instancio.of(BusinessRequest.class)
                .ignore(field(BusinessRequest::getBusinessHours))
                .ignore(field(BusinessRequest::getType))
                .create();
    }

    public BusinessServiceRequest generatedObjectBusinessServiceDto() {
        return Instancio.of(BusinessServiceRequest.class)
                .ignore(field(BusinessServiceRequest::getDuration))
                .create();
    }


//    public EmployeeDto generatedObjectEmployeeDto() {
//        return Instancio.of(EmployeeDto.class)
//                .ignore(field(EmployeeDto::getId))
//                .ignore(field(EmployeeDto::getReservationHistoryDto))
//                .create();
//    }


    public CustomerRequest generatedObjectCustomerDto() {
        return Instancio.of(CustomerRequest.class)
                .create();
    }

    protected String getRequestBody(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }
}
