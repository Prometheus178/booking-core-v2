package org.booking.core.service.customer;

import lombok.RequiredArgsConstructor;
import org.booking.core.domain.request.CustomerRequest;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class EmployeeServiceBean implements EmployeeService {


    public boolean delete(Long id) {
        try {
          //  customerRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public CustomerRequest getById(Long userId) {
//        Optional<Customer> optionalBusinessService = customerRepository.findById(userId);
//        if (optionalBusinessService.isPresent()) {
//            return customerMapper.toDto(optionalBusinessService.get());
//        }
        return null;
    }

}
