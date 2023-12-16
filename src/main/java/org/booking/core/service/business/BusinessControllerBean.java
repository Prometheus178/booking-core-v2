package org.booking.core.service.business;

import org.booking.core.domain.entity.business.Business;
import org.booking.core.repository.BusinessRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessControllerBean implements BusinessController {


    private final BusinessRepository businessRepository;

    public BusinessControllerBean(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    @Override
    public Business create(Business business) {
        return businessRepository.save(business);
    }

    @Override
    public Business update(Long aLong, Business business) {
        Optional<Business> optionalBusiness = businessRepository.findById(aLong);
        if (optionalBusiness.isPresent()) {
            Business existed = optionalBusiness.get();
            existed.setType(business.getType());
            existed.setDescription(business.getDescription());
            existed.setName(business.getName());
            existed.setAddress(business.getAddress());
            return businessRepository.save(existed);
        } else {
            return null;
        }
    }


    @Override
    public boolean delete(Long userId) {
        try {
            businessRepository.deleteById(userId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Business getById(Long aLong) {
        Optional<Business> optionalBusiness = businessRepository.findById(aLong);
        return optionalBusiness.orElse(null);
    }

    @Override
    public List<Business> getAllUsers() {
        return businessRepository.findAll();
    }
}
