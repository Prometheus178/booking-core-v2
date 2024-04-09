package org.booking.core.integration.managment;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.booking.core.request.BusinessHoursRequest;
import org.booking.core.request.BusinessRequest;
import org.booking.core.integration.AbstractIntegrationTest;
import org.booking.core.util.LogActionType;
import org.booking.core.util.LoggerUtil;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

public class BusinessRequestIntegrationTest extends AbstractIntegrationTest {

	public static final String API_BUSINESSES = "/api/v1/managements/businesses/";
	public static Long createdId;

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = BASE_URI;
	}

	@Order(1)
	//@Test
	public void post() {
		BusinessRequest businessRequest = generatedObject();
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
		createdId = response.jsonPath().getLong("id");
		LoggerUtil.logInfo(LogActionType.CREATE, org.booking.core.domain.entity.business.Business.ENTITY_NAME, createdId);

		assertThat(response.jsonPath().getString("name")).isEqualTo(businessRequest.getName());
		assertThat(response.jsonPath().getString("address")).isEqualTo(businessRequest.getAddress());
		assertThat(response.jsonPath().getString("description")).isEqualTo(businessRequest.getDescription());
		assertThat(response.jsonPath().getString("type")).isEqualTo(businessRequest.getType());
	}

	@Order(2)
	////@Test
	public void get() {
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
		LoggerUtil.logInfo(LogActionType.READ, org.booking.core.domain.entity.business.Business.ENTITY_NAME, createdId);


	}

	@Order(3)
	////@Test
	public void update() {
		BusinessRequest businessRequest = generatedObject();
		String requestBody = getRequestBody(businessRequest);
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
		LoggerUtil.logInfo(LogActionType.UPDATE, org.booking.core.domain.entity.business.Business.ENTITY_NAME, createdId);
		assertThat(response.jsonPath().getString("name")).isEqualTo(businessRequest.getName());
		assertThat(response.jsonPath().getString("address")).isEqualTo(businessRequest.getAddress());
		assertThat(response.jsonPath().getString("description")).isEqualTo(businessRequest.getDescription());
		assertThat(response.jsonPath().getString("type")).isEqualTo(businessRequest.getType());
	}

	@Order(4)
	////@Test
	public void getAll() {
		Response response = given()
				.contentType(ContentType.JSON)
				.when()
				.get(API_BUSINESSES)
				.then()
				.extract()
				.response();
		assertThat(response.statusCode())
				.isEqualTo(HttpStatus.OK.value());
	}

	@Order(5)
	////@Test
	public void delete() {
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
		LoggerUtil.logInfo(LogActionType.DELETE, org.booking.core.domain.entity.business.Business.ENTITY_NAME, createdId);

	}


	public BusinessRequest generatedObject() {
		return Instancio.of(BusinessRequest.class)
				.ignore(field(BusinessRequest::getBusinessHours))
				.ignore(field(BusinessRequest::getType))
				.create();
	}

}