package org.booking.core.api;


import org.booking.core.domain.entity.employee.Employee;
import org.booking.core.service.employee.EmployeeController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;

@RestController
@RequestMapping(path = "/api/employees", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeApiController implements ApiController<Employee> {


    private EmployeeController employeeService;

    public EmployeeApiController(EmployeeController employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/")
    @Override
    public ResponseEntity create(Employee obj) {
        return ResponseEntity.ok().body(employeeService.create(obj));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity update(@PathParam("{id}") Long aLong, Employee obj) {
        return ResponseEntity.ok().body(employeeService.update(aLong, obj));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity delete(@PathParam("{id}") Long aLong) {
        return ResponseEntity.ok().body(employeeService.delete(aLong));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity getById(@PathParam("{id}") Long aLong) {
        return ResponseEntity.ok().body(employeeService.getById(aLong));
    }

    @GetMapping("/")
    @Override
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok().body(employeeService.getAllUsers());
    }
}
