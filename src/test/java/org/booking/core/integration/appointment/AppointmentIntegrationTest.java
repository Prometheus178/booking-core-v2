package org.booking.core.integration.appointment;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.entity.reservation.TimeSlot;
import org.booking.core.domain.request.*;
import org.booking.core.domain.request.security.AuthenticationResponse;
import org.booking.core.domain.request.security.BaseRegisterRequest;
import org.booking.core.domain.response.BusinessResponse;
import org.booking.core.domain.response.BusinessServiceResponse;
import org.booking.core.integration.AbstractIntegrationTest;
import org.booking.core.integration.managment.BusinessRequestIntegrationTest;
import org.booking.core.integration.managment.BusinessRequestServiceIntegrationTest;
import org.booking.core.util.LogActionType;
import org.booking.core.util.LoggerUtil;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;
import static org.booking.core.config.security.JwtAuthenticationFilter.AUTHORIZATION;
import static org.booking.core.config.security.JwtAuthenticationFilter.BEARER_;
import static org.instancio.Select.field;

public class AppointmentIntegrationTest extends AbstractIntegrationTest {

	public static final String API_APPOINTMENTS = "/api/v1/customers/appointments";
	public static String customerToken;
	public static String managerToken;
	public static Long createdBusinessServiceId;
	public static Long createdBusinessId;
	public static Long createEmployeeId;
	public static Long createdReservationId;
	public static List<TimeSlot> timeSlots;


	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = BASE_URI;
		managerToken = register("/api/v1/auth/business/register");
		System.out.println(managerToken);
		customerToken = register("/api/v1/auth/register");
		System.out.println(customerToken);

	}

	private static String register(String path) {
		BaseRegisterRequest registerRequest = Instancio.of(BaseRegisterRequest.class).create();

		if (path.contains("business")) {
			String email = registerRequest.getEmail();
			registerRequest.setEmail(email + "business");
		}
		String requestBody = getRequestBody(registerRequest);

		Response response = given()
				.contentType(ContentType.JSON)
				.and()
				.body(requestBody)
				.when()
				.post(path)
				.then()
				.extract()
				.response();
		AuthenticationResponse authenticationResponse = response.body().as(AuthenticationResponse.class);
		return authenticationResponse.getToken();
	}

	@Order(1)
	@Test
	void createBusiness() {
		BusinessRequest businessRequest = Instancio.of(BusinessRequest.class)
				.ignore(field(BusinessRequest::getBusinessHours))
				.ignore(field(BusinessRequest::getType))
				.create();
		BusinessHoursRequest businessHoursRequest = new BusinessHoursRequest();
		businessHoursRequest.setOpenTime(LocalTime.of(10, 0).toString());
		businessHoursRequest.setCloseTime(LocalTime.of(18, 0).toString());
		businessRequest.setBusinessHours(businessHoursRequest);
		businessRequest.setType("BARBERSHOP");
		String requestBody = getRequestBody(businessRequest);
		Response response = given()
				.contentType(ContentType.JSON)
				.header(AUTHORIZATION, BEARER_ + managerToken)
				.and()
				.body(requestBody)
				.when()
				.post(BusinessRequestIntegrationTest.API_BUSINESSES)
				.then()
				.extract()
				.response();

		assertThat(response.statusCode())
				.isEqualTo(HttpStatus.OK.value());
		BusinessResponse businessResponse = response.body().as(BusinessResponse.class);
		createdBusinessId = businessResponse.getId();
		createEmployeeId = businessResponse.getEmployees().stream().findFirst().get();

		LoggerUtil.logInfo(LogActionType.CREATE, Business.ENTITY_NAME, createdBusinessId);

		assertThat(response.jsonPath().getString("name")).isEqualTo(businessRequest.getName());
		assertThat(response.jsonPath().getString("address")).isEqualTo(businessRequest.getAddress());
		assertThat(response.jsonPath().getString("description")).isEqualTo(businessRequest.getDescription());
		assertThat(response.jsonPath().getString("type")).isEqualTo(businessRequest.getType());
	}

	@Order(2)
	@Test
	void createBusinessService() {
		BusinessServiceRequest businessServiceRequest = Instancio.of(BusinessServiceRequest.class)
				.ignore(field(BusinessServiceRequest::getDuration))
				.ignore(field(BusinessServiceRequest::getBusinessId))
				.create();
		businessServiceRequest.setDuration(60);
		businessServiceRequest.setBusinessId(createdBusinessId);
		String requestBody = getRequestBody(businessServiceRequest);
		System.out.println(requestBody);
		Response response = given()
				.contentType(ContentType.JSON)
				.header(AUTHORIZATION, BEARER_ + managerToken)
				.and()
				.body(requestBody)
				.when()
				.post(BusinessRequestServiceIntegrationTest.API_BUSINESSES_SERVICES)
				.then()
				.extract()
				.response();

		assertThat(response.statusCode())
				.isEqualTo(HttpStatus.OK.value());
		BusinessServiceResponse businessServiceResponse = response.body().as(BusinessServiceResponse.class);
		createdBusinessServiceId = businessServiceResponse.getId();

		assertThat(response.jsonPath().getString("name")).isEqualTo(businessServiceRequest.getName());
		assertThat(response.jsonPath().getDouble("price")).isEqualTo(businessServiceRequest.getPrice(),
				withPrecision(2d));
		assertThat(response.jsonPath().getString("description")).isEqualTo(businessServiceRequest.getDescription());
	}

	@Order(3)
	@Test
	void findAvailableTimeSlots() {
		Response response = given()
				.contentType(ContentType.JSON)
				.header(AUTHORIZATION, BEARER_ + customerToken)
				.param("businessServiceId", createdBusinessServiceId)
				.param("day", LocalDate.now().toEpochDay())
				.when()
				.get(API_APPOINTMENTS + "/find/available-time-slots")
				.then()
				.extract()
				.response();
		assertThat(response.statusCode())
				.isEqualTo(HttpStatus.OK.value());
		timeSlots = response.jsonPath().getList(".", TimeSlot.class);
		assertThat(timeSlots).isNotEmpty();
	}

	@Order(4)
	@Test
	void reservation() {
		ReservationRequest reservationRequest = createReservation(0);
		String requestBody = getRequestBody(reservationRequest);
		System.out.println(requestBody);
		Response response = given()
				.contentType(ContentType.JSON)
				.header(AUTHORIZATION, BEARER_ + customerToken)
				.and()
				.body(requestBody)
				.when()
				.post(API_APPOINTMENTS + "/")
				.then()
				.extract()
				.response();

		assertThat(response.statusCode())
				.isEqualTo(HttpStatus.OK.value());
		createdReservationId = response.jsonPath().getLong("id");
	}

	@Order(5)
	@Test
	void modifyReservation() {
		ReservationRequest reservationRequest = createReservation(1);
		String requestBody = getRequestBody(reservationRequest);
		Response response = given()
				.contentType(ContentType.JSON)
				.header(AUTHORIZATION, BEARER_ + customerToken)
				.and()
				.body(requestBody)
				.when()
				.put(API_APPOINTMENTS + "/" + createdReservationId)
				.then()
				.extract()
				.response();

		assertThat(response.statusCode())
				.isEqualTo(HttpStatus.OK.value());
		createdReservationId = response.jsonPath().getLong("id");
	}

	private ReservationRequest createReservation(int i) {
		ReservationRequest reservationRequest = new ReservationRequest();
		reservationRequest.setEmployeeId(createEmployeeId);
		reservationRequest.setBusinessServiceId(createdBusinessServiceId);

		TimeSlot timeSlot = timeSlots.get(i);
		LocalTime startTime = timeSlot.getStartTime();
		LocalTime endTime = timeSlot.getEndTime();
		LocalDate currentDay = LocalDate.now();
		LocalDateTime reservedTime = LocalDateTime.of(currentDay.getYear(), currentDay.getMonth(), currentDay.getDayOfMonth(),
				startTime.getHour(), startTime.getMinute());
		reservationRequest.setBookingTime(reservedTime.toString());
		DurationRequest durationRequest = new DurationRequest();
		durationRequest.setStartTime(reservedTime.toString());
		LocalDateTime endDateTime = LocalDateTime.of(currentDay.getYear(), currentDay.getMonth(),
				currentDay.getDayOfMonth(),
				endTime.getHour(), endTime.getMinute());
		durationRequest.setEndTime(endDateTime.toString());
		reservationRequest.setDuration(durationRequest);
		return reservationRequest;
	}
}
