package org.booking.core.mapper.integration.managment;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.booking.core.mapper.integration.AbstractIntegrationTest;
import org.booking.core.request.BusinessHoursRequest;
import org.booking.core.request.BusinessRequest;
import org.booking.core.request.BusinessServiceRequest;
import org.booking.core.util.LogActionType;
import org.booking.core.util.LoggerUtil;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;
import static org.booking.core.mapper.integration.managment.BusinessRequestIntegrationTest.API_BUSINESSES;
import static org.instancio.Select.field;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BusinessRequestServiceIntegrationTest extends AbstractIntegrationTest {

	public static final String API_BUSINESSES_SERVICES = "/api/v1/managements/business-services/";
	public static Long createdId;
	public static Long createdIdBusinessDto;

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = BASE_URI;
	}

	@Order(0)
	////@Test
	public void postBusinessDto() {
		BusinessRequest businessRequest = generatedBusinessDto();
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

	@Order(1)
	////@Test
	public void post() {
		BusinessServiceRequest businessServiceRequest = generatedObject();
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
		createdId = response.jsonPath().getLong("id");
		assertThat(response.jsonPath().getString("name")).isEqualTo(businessServiceRequest.getName());
		assertThat(response.jsonPath().getDouble("price")).isEqualTo(businessServiceRequest.getPrice(), withPrecision(2d));
		assertThat(response.jsonPath().getString("description")).isEqualTo(businessServiceRequest.getDescription());
	}

	@Order(2)
	////@Test
	public void get() {
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
	////@Test
	public void update() {
		BusinessServiceRequest businessServiceRequest = generatedObject();
		String requestBody = getRequestBody(businessServiceRequest);
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
		assertThat(response.jsonPath().getString("name")).isEqualTo(businessServiceRequest.getName());

		assertThat(response.jsonPath().getDouble("price")).isCloseTo(businessServiceRequest.getPrice(),
				Assertions.within(delta));
		assertThat(response.jsonPath().getString("description")).isEqualTo(businessServiceRequest.getDescription());
	}

	@Order(4)
	////@Test
	public void getAll() {
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
	////@Test
	public void delete() {
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

	@Order(6)
	////@Test
	public void deleteBusinessDto() {
		assertThat(createdIdBusinessDto).isNotNull();
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

	public BusinessServiceRequest generatedObject() {
		return Instancio.of(BusinessServiceRequest.class)
				.ignore(field(BusinessServiceRequest::getDuration))
				.create();
	}


	public BusinessRequest generatedBusinessDto() {
		return Instancio.of(BusinessRequest.class)
				.ignore(field(BusinessRequest::getBusinessHours))
				.ignore(field(BusinessRequest::getType))
				.create();
	}

}