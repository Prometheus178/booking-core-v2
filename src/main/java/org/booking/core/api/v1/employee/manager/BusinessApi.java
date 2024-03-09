package org.booking.core.api.v1.employee.manager;


import org.booking.core.api.Api;
import org.booking.core.domain.dto.BusinessDto;
import org.booking.core.service.business.BusinessController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        path = "/api/v1/managements/businesses",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
public class BusinessApi implements Api<BusinessDto> {

    private final BusinessController businessService;

    public BusinessApi(BusinessController businessService) {
        this.businessService = businessService;
    }

    @PostMapping("/")
    @Override
    public ResponseEntity<BusinessDto> create(@RequestBody BusinessDto obj) {
        return ResponseEntity.ok().body(businessService.create(obj));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<BusinessDto> update(@PathVariable("id") Long aLong, @RequestBody BusinessDto obj) {
        return ResponseEntity.ok().body(businessService.update(aLong, obj));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity delete(@PathVariable("id") Long aLong) {
        return ResponseEntity.ok().body(businessService.delete(aLong));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity getById(@PathVariable("id") Long aLong) {
        return ResponseEntity.ok().body(businessService.getById(aLong));
    }

    @GetMapping("/")
    @Override
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok().body(businessService.getAll());
    }
}
