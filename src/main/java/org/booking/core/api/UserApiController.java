package org.booking.core.api;


import org.booking.core.domain.entity.base.User;
import org.booking.core.repository.EmployeeRepository;
import org.booking.core.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users",
        produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserApiController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<User> getAllUsers() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return employeeRepository.findById(id).get();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return employeeRepository.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        User existingUser = employeeRepository.findById(id).get();
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        return employeeRepository.save(existingUser);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        try {
            employeeRepository.findById(id).get();
            employeeRepository.deleteById(id);
            return "User deleted successfully";
        } catch (Exception e) {
            return "User not found";
        }
    }
}
