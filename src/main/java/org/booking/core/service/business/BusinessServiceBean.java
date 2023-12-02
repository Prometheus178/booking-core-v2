package org.booking.core.service.business;

import org.booking.core.domain.entity.business.Business;
import org.booking.core.repository.BusinessRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceBean implements BusinessService {


    private BusinessRepository businessRepository;

    public BusinessServiceBean(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    @Override
    public Business create(Business business) {
        return businessRepository.save(business);
    }

    @Override
    public Business update(Long userId, Business business) {
        Business existingUser = businessRepository.findById(userId).get();
// TODO: 02.12.2023 update
        return businessRepository.save(existingUser);
    }


    @Override
    public boolean delete(Long userId) {
        try {
            businessRepository.findById(userId).get();
            businessRepository.deleteById(userId);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public Business getById(Long userId) {
        return businessRepository.findById(userId).get();
    }

    @Override
    public List<Business> getAllUsers() {
        return businessRepository.findAll();
    }
}
