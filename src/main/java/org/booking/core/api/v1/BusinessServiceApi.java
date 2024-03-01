package org.booking.core.api.v1;


import org.booking.core.api.Api;
import org.booking.core.domain.dto.BusinessServiceDto;
import org.booking.core.service.business.service.BusinessServiceController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/business-services", produces = MediaType.APPLICATION_JSON_VALUE, consumes =
        MediaType.APPLICATION_JSON_VALUE)
public class BusinessServiceApi implements Api<BusinessServiceDto> {


    private final BusinessServiceController businessServiceController;

    public BusinessServiceApi(BusinessServiceController businessServiceController) {
        this.businessServiceController = businessServiceController;
    }

    @PostMapping("/")
    @Override
    public ResponseEntity create(@RequestBody BusinessServiceDto obj) {
        return ResponseEntity.ok().body(businessServiceController.create(obj));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity update(@PathVariable("id") Long aLong, @RequestBody BusinessServiceDto obj) {
        return ResponseEntity.ok().body(businessServiceController.update(aLong, obj));
    }


    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity delete(@PathVariable("id") Long aLong) {
        return ResponseEntity.ok().body(businessServiceController.delete(aLong));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity getById(@PathVariable("id") Long aLong) {
        return ResponseEntity.ok().body(businessServiceController.getById(aLong));
    }

    @GetMapping("/")
    @Override
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok().body(businessServiceController.getAll());
    }
}
