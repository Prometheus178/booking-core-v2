package org.booking.core.service.customer;

import org.booking.core.domain.dto.CustomerDto;
import org.booking.core.domain.entity.customer.Customer;
import org.booking.core.mapper.CustomerMapper;
import org.booking.core.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerControllerBean implements CustomerController {


    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerControllerBean(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerDto create(CustomerDto obj) {
        Customer businessService = customerMapper.dtoTo(obj);
        Customer save = customerRepository.save(businessService);
        return customerMapper.toDto(save);
    }

    @Override
    public CustomerDto update(Long aLong, CustomerDto obj) {
        Optional<Customer> optionalBusinessService = customerRepository.findById(aLong);
        if (optionalBusinessService.isPresent()) {
            Customer existed = optionalBusinessService.get();
            Customer businessService = customerMapper.dtoTo(obj);
            existed.setEmail(businessService.getEmail());
            existed.setName(businessService.getName());
            Customer save = customerRepository.save(businessService);
            return customerMapper.toDto(save);
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            customerRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public CustomerDto getById(Long userId) {
        Optional<Customer> optionalBusinessService = customerRepository.findById(userId);
        if (optionalBusinessService.isPresent()) {
            return customerMapper.toDto(optionalBusinessService.get());
        }
        return null;
    }

    @Override
    public List<CustomerDto> getAll() {
        List<Customer> all = customerRepository.findAll();
        if (!all.isEmpty()) {
            List<CustomerDto> services = new ArrayList<>();
            all.forEach(business -> {
                services.add(customerMapper.toDto(business));
            });
            return services;
        }
        return null;
    }
}
