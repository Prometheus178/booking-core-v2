package org.booking.core.mapper;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import org.booking.core.domain.dto.ReservationDto;
import org.booking.core.domain.entity.business.service.BusinessService;
import org.booking.core.domain.entity.customer.Customer;
import org.booking.core.domain.entity.employee.Employee;
import org.booking.core.domain.entity.reservation.Reservation;
import org.booking.core.repository.BusinessServiceRepository;
import org.booking.core.repository.CustomerRepository;
import org.booking.core.repository.EmployeeRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class ReservationMapper {

    static ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private EmployeeRepository employeeRepository;

    @Inject
    private BusinessServiceRepository businessServiceRepository;

    @Mapping(source = "customer", target = "customerId")
    @Mapping(source = "service", target = "serviceId")
    @Mapping(source = "employee", target = "employeeId")
    public abstract ReservationDto toDto(Reservation obj);
    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "serviceId", target = "service")
    @Mapping(source = "employeeId", target = "employee")
    public abstract Reservation toEntity(ReservationDto dto);

    protected Customer fromLongToCustomer(Long customerId) throws EntityNotFoundException {
        if (customerId == null){
            return null;
        }
        Optional<Customer> optionalEntity = customerRepository.findById(customerId);
        if (optionalEntity.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return optionalEntity.get();
    }

    protected Employee fromLongToEmployee(Long employeeId) throws EntityNotFoundException {
        if (employeeId == null){
            return null;
        }
        Optional<Employee> optionalEntity = employeeRepository.findById(employeeId);
        if (optionalEntity.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return optionalEntity.get();
    }

    protected BusinessService fromLongToBusinessService(Long serviceId) throws EntityNotFoundException {
        if (serviceId == null){
            return null;
        }
        Optional<BusinessService> optionalEntity = businessServiceRepository.findById(serviceId);
        if (optionalEntity.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return optionalEntity.get();
    }

    protected Long fromEmployeeToLong(Employee employee) throws EntityNotFoundException {
        if (employee == null){
            return null;
        }
        return employee.getId();
    }

    protected Long fromBusinessServiceToLong(BusinessService businessService) throws EntityNotFoundException {
        if (businessService == null){
            return null;
        }
        return businessService.getId();
    }

    protected Long fromCustomerToLong(Customer customer) throws EntityNotFoundException {
        if (customer == null){
            return null;
        }
        return customer.getId();
    }
}
