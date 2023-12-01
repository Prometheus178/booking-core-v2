package org.booking.core.service.employee;

import org.booking.core.domain.entity.employee.Employee;
import org.booking.core.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceBean implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee Employee) {
        return employeeRepository.save(Employee);
    }

    @Override
    public Employee update(Long userId, Employee Employee) {
        Employee existingUser = employeeRepository.findById(userId).get();
        existingUser.setName(Employee.getName());
        existingUser.setEmail(Employee.getEmail());
        return employeeRepository.save(existingUser);
    }

    @Override
    public boolean delete(Long userId) {
        try {
            employeeRepository.findById(userId).get();
            employeeRepository.deleteById(userId);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public Employee getUserById(Long userId) {
        return employeeRepository.findById(userId).get();
    }

    @Override
    public List<Employee> getAllUsers() {
        return employeeRepository.findAll();
    }
}
