package org.booking.core.api;


import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.booking.core.domain.entity.employee.Employee;
import org.booking.core.service.employee.EmployeeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/employees", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeApiController implements ApiController<Employee> {


    private EmployeeService employeeService;

    public EmployeeApiController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/")
    @Override
    public Response create(Employee obj) {
        return Response.ok().entity(employeeService.create(obj)).build();
    }

    @PutMapping("/{id}")
    @Override
    public Response update(@PathParam("{id}") Long aLong, Employee obj) {
        return Response.ok().entity(employeeService.update(aLong, obj)).build();
    }

    @DeleteMapping("/{id}")
    @Override
    public Response delete(@PathParam("{id}") Long aLong) {
        return Response.ok().entity(employeeService.delete(aLong)).build();
    }

    @GetMapping("/{id}")
    @Override
    public Response getById(@PathParam("{id}") Long aLong) {
        return Response.ok().entity(employeeService.getById(aLong)).build();
    }

    @GetMapping("/")
    @Override
    public Response getAllUsers() {
        return Response.ok().entity(employeeService.getAllUsers()).build();
    }
}
