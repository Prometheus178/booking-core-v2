package org.booking.core.service.business.service;

import org.booking.core.domain.dto.BusinessServiceDto;
import org.booking.core.domain.entity.business.service.BusinessService;
import org.booking.core.mapper.BusinessServiceMapper;
import org.booking.core.repository.BusinessServiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessServiceControllerBean implements BusinessServiceController {


    private final BusinessServiceRepository businessServiceRepository;
    private final BusinessServiceMapper businessServiceMapper;

    public BusinessServiceControllerBean(BusinessServiceRepository businessServiceRepository, BusinessServiceMapper businessServiceMapper) {
        this.businessServiceRepository = businessServiceRepository;
        this.businessServiceMapper = businessServiceMapper;
    }

    @Override
    public BusinessServiceDto create(BusinessServiceDto obj) {
        BusinessService businessService = businessServiceMapper.dtoTo(obj);
        BusinessService save = businessServiceRepository.save(businessService);
        return businessServiceMapper.toDto(save);
    }

    @Override
    public BusinessServiceDto update(Long aLong, BusinessServiceDto obj) {
        Optional<BusinessService> optionalBusinessService = businessServiceRepository.findById(aLong);
        if (optionalBusinessService.isPresent()) {
            BusinessService existed = optionalBusinessService.get();
            BusinessService businessService = businessServiceMapper.dtoTo(obj);
            existed.setDescription(businessService.getDescription());
            existed.setPrice(businessService.getPrice());
            existed.setName(businessService.getName());
            BusinessService save = businessServiceRepository.save(businessService);
            return businessServiceMapper.toDto(save);
        } else {
            return null;
        }
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
    public BusinessServiceDto getById(Long userId) {
        Optional<BusinessService> optionalBusinessService = businessServiceRepository.findById(userId);
        if (optionalBusinessService.isPresent()) {
            return businessServiceMapper.toDto(optionalBusinessService.get());
        }
        return null;
    }

    @Override
    public List<BusinessServiceDto> getAll() {
        List<BusinessService> all = businessServiceRepository.findAll();
        if (!all.isEmpty()) {
            List<BusinessServiceDto> services = new ArrayList<>();
            all.forEach(business -> {
                services.add(businessServiceMapper.toDto(business));
            });
            return services;
        }
        return null;
    }
}
