package org.booking.core.api.v1.manager;


import lombok.RequiredArgsConstructor;
import org.booking.core.api.BaseApi;
import org.booking.core.domain.request.BusinessRequest;
import org.booking.core.domain.response.BusinessResponse;
import org.booking.core.service.business.BusinessService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(
		path = "/api/v1/managements/businesses",
		produces = MediaType.APPLICATION_JSON_VALUE,
		consumes = MediaType.APPLICATION_JSON_VALUE
)
public class BusinessApi implements BaseApi<BusinessRequest, BusinessResponse, Long> {

	private final BusinessService businessService;

	@PostMapping("/")
	@Override
	public ResponseEntity<BusinessResponse> create(@RequestBody BusinessRequest obj) {
		return ResponseEntity.ok().body(businessService.create(obj));
	}

	@PutMapping("/{id}")
	@Override
	public ResponseEntity<BusinessResponse> update(@PathVariable("id") Long aLong, @RequestBody BusinessRequest obj) {
		return ResponseEntity.ok().body(businessService.update(aLong, obj));
	}

	@DeleteMapping("/{id}")
	@Override
	public ResponseEntity<Boolean> delete(@PathVariable("id") Long aLong) {
		return ResponseEntity.ok().body(businessService.delete(aLong));
	}

	@GetMapping("/{id}")
	@Override
	public ResponseEntity<BusinessResponse> getById(@PathVariable("id") Long aLong) {
		return ResponseEntity.ok().body(businessService.getById(aLong));
	}

}
