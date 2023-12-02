package org.booking.core.api;


import jakarta.ws.rs.PathParam;
import org.booking.core.domain.entity.business.BusinessService;
import org.booking.core.service.business.service.BusinessServiceController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/businesses-service", produces = MediaType.APPLICATION_JSON_VALUE, consumes =
        MediaType.APPLICATION_JSON_VALUE)
public class BusinessServiceApiController implements ApiController<BusinessService> {


    private BusinessServiceController businessServiceController;

    public BusinessServiceApiController(BusinessServiceController businessServiceController) {
        this.businessServiceController = businessServiceController;
    }

    @PostMapping("/")
    @Override
    public ResponseEntity create(BusinessService obj) {
        return ResponseEntity.ok().body(businessServiceController.create(obj));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity update(@PathParam("{id}") Long aLong, BusinessService obj) {
        return ResponseEntity.ok().body(businessServiceController.update(aLong, obj));
    }


    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity delete(@PathParam("{id}") Long aLong) {
        return ResponseEntity.ok().body(businessServiceController.delete(aLong));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity getById(@PathParam("{id}") Long aLong) {
        return ResponseEntity.ok().body(businessServiceController.getById(aLong));
    }

    @GetMapping("/")
    @Override
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok().body(businessServiceController.getAllUsers());
    }
}
