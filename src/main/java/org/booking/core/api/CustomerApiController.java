package org.booking.core.api;


import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.booking.core.domain.entity.customer.Customer;
import org.booking.core.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public Response create(Customer obj) {
        return Response.ok().entity(customerService.create(obj)).build();
    }

    @PutMapping("/{id}")
    @Override
    public Response update(@PathParam("{id}") Long aLong, Customer obj) {
        return Response.ok().entity(customerService.update(aLong, obj)).build();
    }

    @DeleteMapping("/{id}")
    @Override
    public Response delete(@PathParam("{id}") Long aLong) {
        return Response.ok().entity(customerService.delete(aLong)).build();
    }

    @GetMapping("/{id}")
    @Override
    public Response getById(@PathParam("{id}") Long aLong) {
        return Response.ok().entity(customerService.getById(aLong)).build();
    }

    @GetMapping("/")
    @Override
    public Response getAllUsers() {
        return Response.ok().entity(customerService.getAllUsers()).build();
    }
}
