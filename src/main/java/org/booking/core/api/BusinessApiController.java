package org.booking.core.api;


import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.service.business.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public Response create(Business obj) {
        return Response.ok().entity(businessService.create(obj)).build();
    }

    @PutMapping("/{id}")
    @Override
    public Response update(@PathParam("{id}") Long aLong, Business obj) {
        return Response.ok().entity(businessService.update(aLong, obj)).build();
    }

    @DeleteMapping("/{id}")
    @Override
    public Response delete(@PathParam("{id}") Long aLong) {
        return Response.ok().entity(businessService.delete(aLong)).build();
    }

    @GetMapping("/{id}")
    @Override
    public Response getById(@PathParam("{id}") Long aLong) {
        return Response.ok().entity(businessService.getById(aLong)).build();
    }

    @GetMapping("/")
    @Override
    public Response getAllUsers() {
        return Response.ok().entity(businessService.getAllUsers()).build();
    }
}
