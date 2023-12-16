package org.booking.core.api;


import lombok.extern.java.Log;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.service.business.BusinessController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log
@RestController
@RequestMapping(path = "/api/businesses", produces = MediaType.APPLICATION_JSON_VALUE, consumes =
        MediaType.APPLICATION_JSON_VALUE)
public class BusinessApi implements Api<Business> {

    private BusinessController businessService;

    public BusinessApi(BusinessController businessService) {
        this.businessService = businessService;
    }

    @PostMapping("/")
    @Override
    public ResponseEntity create(@RequestBody Business obj) {
        return ResponseEntity.ok().body(businessService.create(obj));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity update(@PathVariable("id") Long aLong, @RequestBody Business obj) {
        return ResponseEntity.ok().body(businessService.update(aLong, obj));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity delete(@PathVariable("id") Long aLong) {
        log.info(aLong.toString());
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
        return ResponseEntity.ok().body(businessService.getAllUsers());
    }
}
