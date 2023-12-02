package org.booking.core.api;


import jakarta.ws.rs.PathParam;
import org.booking.core.domain.entity.customer.Customer;
import org.booking.core.service.customer.CustomerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/customers", produces = MediaType.APPLICATION_JSON_VALUE, consumes =
        MediaType.APPLICATION_JSON_VALUE)
public class CustomerApiController implements ApiController<Customer> {

    private CustomerService customerService;

    public CustomerApiController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/")
    @Override
    public ResponseEntity create(Customer obj) {
        return ResponseEntity.ok().body(customerService.create(obj));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity update(@PathParam("{id}") Long aLong, Customer obj) {
        return ResponseEntity.ok().body(customerService.update(aLong, obj));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity delete(@PathParam("{id}") Long aLong) {
        return ResponseEntity.ok().body(customerService.delete(aLong));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity getById(@PathParam("{id}") Long aLong) {
        return ResponseEntity.ok().body(customerService.getById(aLong));
    }

    @GetMapping("/")
    @Override
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok().body(customerService.getAllUsers());
    }
}
