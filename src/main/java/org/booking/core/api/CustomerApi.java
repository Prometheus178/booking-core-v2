package org.booking.core.api;


import org.booking.core.domain.dto.CustomerDto;
import org.booking.core.domain.entity.customer.Customer;
import org.booking.core.service.customer.CustomerController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/customers", produces = MediaType.APPLICATION_JSON_VALUE, consumes =
        MediaType.APPLICATION_JSON_VALUE)
public class CustomerApi implements Api<CustomerDto> {

    private final CustomerController customerService;

    public CustomerApi(CustomerController customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/")
    @Override
    public ResponseEntity create(@RequestBody CustomerDto obj) {
        return ResponseEntity.ok().body(customerService.create(obj));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity update(@PathVariable("id") Long aLong,@RequestBody CustomerDto obj) {
        return ResponseEntity.ok().body(customerService.update(aLong, obj));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity delete(@PathVariable("id") Long aLong) {
        return ResponseEntity.ok().body(customerService.delete(aLong));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity getById(@PathVariable("id") Long aLong) {
        return ResponseEntity.ok().body(customerService.getById(aLong));
    }

    @GetMapping("/")
    @Override
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok().body(customerService.getAll());
    }
}
