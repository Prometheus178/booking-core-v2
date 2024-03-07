package org.booking.core.service.customer;

import lombok.RequiredArgsConstructor;
import org.booking.core.domain.dto.CustomerDto;
import org.booking.core.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class CustomerControllerBean implements CustomerController {



    private final CustomerMapper customerMapper;



    @Override
    public CustomerDto create(CustomerDto obj) {
//        Customer businessService = customerMapper.toEntity(obj);
//        Customer save = customerRepository.save(businessService);
//        return customerMapper.toDto(save);
        return null;
    }

    @Override
    public CustomerDto update(Long aLong, CustomerDto obj) {
//        Optional<Customer> optionalBusinessService = customerRepository.findById(aLong);
//        if (optionalBusinessService.isPresent()) {
//            Customer existed = optionalBusinessService.get();
//            User existedUser = existed.getUser();
//            Customer customer = customerMapper.toEntity(obj);
//            User user = customer.getUser();
//            existedUser.setEmail(user.getEmail());
//            existedUser.setName(user.getName());
//            Customer save = customerRepository.save(customer);
//            return customerMapper.toDto(save);
//        } else {
            return null;
//        }
    }

    @Override
    public boolean delete(Long id) {
        try {
          //  customerRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public CustomerDto getById(Long userId) {
//        Optional<Customer> optionalBusinessService = customerRepository.findById(userId);
//        if (optionalBusinessService.isPresent()) {
//            return customerMapper.toDto(optionalBusinessService.get());
//        }
        return null;
    }

    @Override
    public List<CustomerDto> getAll() {
//        List<Customer> all = customerRepository.findAll();
//        if (!all.isEmpty()) {
//            List<CustomerDto> services = new ArrayList<>();
//            all.forEach(business -> {
//                services.add(customerMapper.toDto(business));
//            });
//            return services;
//        }
        return null;
    }
}
