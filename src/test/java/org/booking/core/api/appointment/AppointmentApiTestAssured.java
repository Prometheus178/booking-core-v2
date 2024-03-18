package org.booking.core.api.appointment;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.booking.core.domain.entity.reservation.TimeSlot;
import org.booking.core.domain.request.*;
import org.booking.core.util.LogActionType;
import org.booking.core.util.LoggerUtil;
import org.instancio.Instancio;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;
import static org.booking.core.api.AbstractApiTestAssured.BASE_URI;
import static org.booking.core.api.BusinessRequestApiTestAssured.API_BUSINESSES;
import static org.booking.core.api.BusinessRequestServiceApiTestAssured.API_BUSINESSES_SERVICES;
import static org.booking.core.api.EmployeeApiTestAssured.API_CUSTOMERS;
import static org.instancio.Select.field;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppointmentApiTestAssured {

    public static final String API_APPOINTMENTS = "/api/appointments/";
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

    @Order(1)
    @Test
    void postBusinessDto() {
        BusinessRequest businessRequest = generatedObjectBusinessDto();
        BusinessHoursRequest businessHoursRequest = new BusinessHoursRequest();
        businessHoursRequest.setOpenTime(LocalTime.of(10, 0).toString());
        businessHoursRequest.setCloseTime(LocalTime.of(18, 0).toString());
        businessRequest.setBusinessHours(businessHoursRequest);
        businessRequest.setType("BARBERSHOP");
        String requestBody = getRequestBody(businessRequest);
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
        LoggerUtil.logInfo(LogActionType.CREATE, org.booking.core.domain.entity.business.Business.ENTITY_NAME, createdIdBusinessDto);

        assertThat(response.jsonPath().getString("name")).isEqualTo(businessRequest.getName());
        assertThat(response.jsonPath().getString("address")).isEqualTo(businessRequest.getAddress());
        assertThat(response.jsonPath().getString("description")).isEqualTo(businessRequest.getDescription());
        assertThat(response.jsonPath().getString("type")).isEqualTo(businessRequest.getType());
    }

    @Order(2)
    @Test
    void postBusinessServiceDto() {
        BusinessServiceRequest businessServiceRequest = generatedObjectBusinessServiceDto();
        businessServiceRequest.setDuration(60);
        String requestBody = getRequestBody(businessServiceRequest);
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
        assertThat(response.jsonPath().getString("name")).isEqualTo(businessServiceRequest.getName());
        assertThat(response.jsonPath().getDouble("price")).isEqualTo(businessServiceRequest.getPrice(),
                withPrecision(2d));
        assertThat(response.jsonPath().getString("description")).isEqualTo(businessServiceRequest.getDescription());
    }


//    @Order(3)
//    @Test
//    void postEmployeeDto() {
//        EmployeeDto employeeDto = generatedObjectEmployeeDto();
//        Set<Long> ids = new HashSet<>();
//        ids.add(createdIdBusinessDto);
//        employeeDto.setBusinessIds(ids);
//
//        String requestBody = getRequestBody(employeeDto);
//        Response response = given()
//                .contentType(ContentType.JSON)
//                .and()
//                .body(requestBody)
//                .when()
//                //.post(API_EMPLOYEES)
//                .then()
//              //  .extract()
//                .response();
//
//        assertThat(response.statusCode())
//                .isEqualTo(HttpStatus.OK.value());
//        createdIdEmployeeDto = response.jsonPath().getLong("id");
//        assertThat(response.jsonPath().getString("name")).isEqualTo(employeeDto.getName());
//        assertThat(response.jsonPath().getString("email")).isEqualTo(employeeDto.getEmail());
//    }
    @Order(4)
    @Test
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
        timeSlots = response.jsonPath().getList(".", TimeSlot.class);
        assertThat(timeSlots).isNotEmpty();
    }

    @Order(6)
    @Test
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
    @Test
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
