package org.booking.core.api.v1.manager;


import lombok.RequiredArgsConstructor;
import org.booking.core.api.BaseApi;
import org.booking.core.domain.request.BusinessServiceRequest;
import org.booking.core.domain.response.BusinessServiceResponse;
import org.booking.core.service.business.service.BusinessServiceService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/managements/business-services", produces = MediaType.APPLICATION_JSON_VALUE, consumes =
        MediaType.APPLICATION_JSON_VALUE)
public class BusinessServiceApi implements BaseApi<BusinessServiceRequest, BusinessServiceResponse, Long> {


    private final BusinessServiceService businessServiceService;

    @Override
    public ResponseEntity<BusinessServiceResponse> create(@RequestBody BusinessServiceRequest obj) {
        return ResponseEntity.ok().body(businessServiceService.create(obj));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<BusinessServiceResponse> update(@PathVariable("id") Long aLong, BusinessServiceRequest obj) {
        return ResponseEntity.ok().body(businessServiceService.update(aLong, obj));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long aLong) {
        return ResponseEntity.ok().body(businessServiceService.delete(aLong));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<BusinessServiceResponse> getById(@PathVariable("id") Long aLong) {
        return ResponseEntity.ok().body(businessServiceService.getById(aLong));
    }

}
