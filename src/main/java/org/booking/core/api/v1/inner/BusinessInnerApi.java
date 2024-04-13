package org.booking.core.api.v1.inner;


import lombok.RequiredArgsConstructor;
import org.booking.core.response.BusinessServiceResponse;
import org.booking.core.service.business.service.BusinessServiceService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/inner/managements", produces = MediaType.APPLICATION_JSON_VALUE, consumes =
		MediaType.APPLICATION_JSON_VALUE)
public class BusinessInnerApi {

	private final BusinessServiceService businessServiceService;

	@GetMapping("/{id}")
	public ResponseEntity<BusinessServiceResponse> getById(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(businessServiceService.getById(id));
	}

}
