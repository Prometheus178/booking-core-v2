package org.booking.core.api.v1.employee;


import lombok.RequiredArgsConstructor;
import org.booking.core.service.customer.EmployeeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/managements/employees", produces = MediaType.APPLICATION_JSON_VALUE, consumes =
        MediaType.APPLICATION_JSON_VALUE)
public class EmployeeApi {

    private final EmployeeService employeeService;

//    @GetMapping("/{id}")
//    public ResponseEntity getById(@PathVariable("id") Long aLong) {
//        return ResponseEntity.ok().body(employeeService.getById(aLong));
//    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity delete(@PathVariable("id") Long aLong) {
//        return ResponseEntity.ok().body(employeeService.delete(aLong));
//    }



}
