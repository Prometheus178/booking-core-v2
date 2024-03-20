package org.booking.core.integration.appointment;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.request.BusinessHoursRequest;
import org.booking.core.domain.request.BusinessRequest;
import org.booking.core.domain.request.BusinessServiceRequest;
import org.booking.core.domain.request.security.AuthenticationResponse;
import org.booking.core.domain.request.security.BaseRegisterRequest;
import org.booking.core.domain.response.BusinessResponse;
import org.booking.core.domain.response.BusinessServiceResponse;
import org.booking.core.integration.managment.BusinessRequestIntegrationTest;
import org.booking.core.integration.managment.BusinessRequestServiceIntegrationTest;
import org.booking.core.util.LogActionType;
import org.booking.core.util.LoggerUtil;
import org.instancio.Instancio;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;
import static org.booking.core.config.security.JwtAuthenticationFilter.AUTHORIZATION;
import static org.booking.core.config.security.JwtAuthenticationFilter.BEARER_;
import static org.instancio.Select.field;

//@WebMvcTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ManagementIntegrationTest extends ManagementRegisterIntegrationTest {

	public static Long createdId;
	public static Long createdBusinessServiceId;
	public static Long createdBusinessId;
	public static Long createdIdEmployeeDto;

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = BASE_URI;
		token = register();
	}

	protected static String register() {
		if (token != null) {
			return token;
		}

		BaseRegisterRequest registerRequest = Instancio.of(BaseRegisterRequest.class).create();
		String requestBody = getRequestBody(registerRequest);

		Response response = given()
				.contentType(ContentType.JSON)
				.and()
				.body(requestBody)
				.when()
				.post("/api/v1/auth/business/register")
				.then()
				.extract()
				.response();
		AuthenticationResponse authenticationResponse = response.body().as(AuthenticationResponse.class);
		return authenticationResponse.getToken();
	}

	@Order(1)
	@Test
	void postBusinessDto() {
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
				.header(AUTHORIZATION, BEARER_ + token)
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

		LoggerUtil.logInfo(LogActionType.CREATE, Business.ENTITY_NAME, createdBusinessId);

		assertThat(response.jsonPath().getString("name")).isEqualTo(businessRequest.getName());
		assertThat(response.jsonPath().getString("address")).isEqualTo(businessRequest.getAddress());
		assertThat(response.jsonPath().getString("description")).isEqualTo(businessRequest.getDescription());
		assertThat(response.jsonPath().getString("type")).isEqualTo(businessRequest.getType());
	}

	@Order(2)
	@Test
	void postBusinessServiceDto() {
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
				.header(AUTHORIZATION, BEARER_ + token)
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


//    @Order(3)
//    //@Test
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

}
