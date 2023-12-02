package org.booking.core.api;


import jakarta.ws.rs.PathParam;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.service.business.BusinessService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/businesses", produces = MediaType.APPLICATION_JSON_VALUE, consumes =
        MediaType.APPLICATION_JSON_VALUE)
public class BusinessApiController implements ApiController<Business> {


    private BusinessService businessService;

    public BusinessApiController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @PostMapping("/")
    @Override
    public ResponseEntity create(Business obj) {
        return ResponseEntity.ok().body(businessService.create(obj));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity update(@PathParam("{id}") Long aLong, Business obj) {
        return ResponseEntity.ok().body(businessService.update(aLong, obj));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity delete(@PathParam("{id}") Long aLong) {
        return ResponseEntity.ok().body(businessService.delete(aLong));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity getById(@PathParam("{id}") Long aLong) {
        return ResponseEntity.ok().body(businessService.getById(aLong));
    }

    @GetMapping("/")
    @Override
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok().body(businessService.getAllUsers());
    }
}
