package org.booking.core.service.business.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.entity.business.service.BusinessServiceEntity;
import org.booking.core.domain.entity.user.User;
import org.booking.core.domain.request.BusinessServiceRequest;
import org.booking.core.domain.response.BusinessServiceResponse;
import org.booking.core.mapper.BusinessServiceMapper;
import org.booking.core.repository.BusinessServiceRepository;
import org.booking.core.service.UserService;
import org.booking.core.service.business.BusinessService;

import java.util.Optional;

@Log
@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class BusinessServiceServiceBean implements BusinessServiceService {

    private final UserService userService;
    private final BusinessService businessService;
    private final BusinessServiceRepository businessServiceRepository;
    private final BusinessServiceMapper businessServiceMapper;

    @Override
    public BusinessServiceResponse create(BusinessServiceRequest obj) {
        BusinessServiceEntity businessServiceEntity = businessServiceMapper.toEntity(obj);
        User currentUser = userService.getCurrentUser();
        Business business = businessServiceEntity.getBusiness();
        if (!business.isEmployeeOfBusiness(currentUser)) {
            throw new RuntimeException("User is not employee of business!");
        }
        businessServiceEntity.setModifiedByUser(currentUser);
        BusinessServiceEntity save = businessServiceRepository.save(businessServiceEntity);
        log.info("Created new business service by: " + currentUser.getEmail());
        return businessServiceMapper.toDto(save);
    }

    @Override
    public BusinessServiceResponse update(Long id, BusinessServiceRequest obj) {
        Optional<BusinessServiceEntity> optionalBusinessService = businessServiceRepository.findById(id);
        BusinessServiceEntity existed = optionalBusinessService.orElseThrow(EntityNotFoundException::new);
        BusinessServiceEntity businessServiceEntity = businessServiceMapper.toEntity(obj);
        existed.setDescription(businessServiceEntity.getDescription());
        existed.setPrice(businessServiceEntity.getPrice());
        existed.setName(businessServiceEntity.getName());
        BusinessServiceEntity save = businessServiceRepository.save(businessServiceEntity);
        return businessServiceMapper.toDto(save);
    }

    @Override
    public boolean delete(Long id) {
        try {
            businessServiceRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public BusinessServiceResponse getById(Long id) {
        Optional<BusinessServiceEntity> optionalBusinessService = businessServiceRepository.findById(id);
        BusinessServiceEntity existed = optionalBusinessService.orElseThrow(EntityNotFoundException::new);
        return businessServiceMapper.toDto(optionalBusinessService.get());
    }

}
