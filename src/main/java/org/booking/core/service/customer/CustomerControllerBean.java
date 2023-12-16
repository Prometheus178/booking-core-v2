package org.booking.core.service.customer;

import org.booking.core.domain.entity.customer.Customer;
import org.booking.core.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerControllerBean implements CustomerController {


    private CustomerRepository customerRepository;

    public CustomerControllerBean(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Long userId, Customer customer) {
        Customer existingUser = customerRepository.findById(userId).get();
        existingUser.setName(customer.getName());
        existingUser.setEmail(customer.getEmail());
        return customerRepository.save(existingUser);
    }


    @Override
    public boolean delete(Long userId) {
        try {
            customerRepository.findById(userId).get();
            customerRepository.deleteById(userId);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public Customer getById(Long userId) {
        return customerRepository.findById(userId).get();
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
}
