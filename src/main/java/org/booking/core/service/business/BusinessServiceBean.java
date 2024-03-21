package org.booking.core.service.business;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.entity.user.User;
import org.booking.core.domain.request.BusinessRequest;
import org.booking.core.domain.response.BusinessResponse;
import org.booking.core.mapper.BusinessMapper;
import org.booking.core.repository.BusinessRepository;
import org.booking.core.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log
@RequiredArgsConstructor
@Service
public class BusinessServiceBean implements BusinessService {

    private final BusinessRepository businessRepository;
    private final BusinessMapper businessMapper;
    private final UserService userService;

    @Override
    public BusinessResponse create(BusinessRequest request) {
        Business business = businessMapper.toEntity(request);
        User currentUser = userService.getCurrentUser();
        business.getEmployees().add(currentUser);
        Business saved = businessRepository.save(business);
        log.info("Created new business by: " + currentUser.getEmail());
        return businessMapper.toResponse(saved);
    }

    @Override
    public BusinessResponse update(Long id, BusinessRequest request) {
        Optional<Business> optionalBusiness = businessRepository.findById(id);
        Business existed = optionalBusiness.orElseThrow(EntityNotFoundException::new);
        Business business = businessMapper.toEntity(request);
            existed.setType(business.getType());
            existed.setDescription(business.getDescription());
            existed.setName(business.getName());
            existed.setAddress(business.getAddress());
            Business saved = businessRepository.save(existed);
        return businessMapper.toResponse(saved);
    }

    @Override
    public boolean delete(Long id) {
        try {
            businessRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public BusinessResponse getById(Long id) {
        Optional<Business> optionalBusiness = businessRepository.findById(id);
        Business existed = optionalBusiness.orElseThrow(EntityNotFoundException::new);
        return businessMapper.toResponse(optionalBusiness.get());
    }

}
