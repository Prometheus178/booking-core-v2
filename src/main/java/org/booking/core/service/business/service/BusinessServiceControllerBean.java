package org.booking.core.service.business.service;

import org.booking.core.repository.BusinessServiceRepository;
import org.booking.core.domain.entity.business.BusinessService;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class BusinessServiceControllerBean implements BusinessServiceController {


    private BusinessServiceRepository businessServiceRepository;

    public BusinessServiceControllerBean(BusinessServiceRepository businessServiceRepository) {
        this.businessServiceRepository = businessServiceRepository;
    }

    @Override
    public BusinessService create(BusinessService reservation) {
        return businessServiceRepository.save(reservation);
    }

    @Override
    public BusinessService update(Long id, BusinessService reservation) {
        BusinessService existingUser = businessServiceRepository.findById(id).get();
    // TODO: 02.12.2023 update
        return businessServiceRepository.save(existingUser);
    }


    @Override
    public boolean delete(Long id) {
        try {
            businessServiceRepository.findById(id).get();
            businessServiceRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public BusinessService getById(Long userId) {
        return businessServiceRepository.findById(userId).get();
    }

    @Override
    public List<BusinessService> getAllUsers() {
        return businessServiceRepository.findAll();
    }
}
