package org.booking.core.service.business.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.booking.core.domain.entity.business.service.BusinessService;
import org.booking.core.domain.request.BusinessServiceRequest;
import org.booking.core.domain.response.BusinessServiceResponse;
import org.booking.core.mapper.BusinessServiceMapper;
import org.booking.core.repository.BusinessServiceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BusinessServiceServiceBean implements BusinessServiceService {


    private final BusinessServiceRepository businessServiceRepository;
    private final BusinessServiceMapper businessServiceMapper;

    @Override
    public BusinessServiceResponse create(BusinessServiceRequest obj) {
        BusinessService businessService = businessServiceMapper.toEntity(obj);
        BusinessService save = businessServiceRepository.save(businessService);
        return businessServiceMapper.toDto(save);
    }

    @Override
    public BusinessServiceResponse update(Long id, BusinessServiceRequest obj) {
        Optional<BusinessService> optionalBusinessService = businessServiceRepository.findById(id);
        BusinessService existed = optionalBusinessService.orElseThrow(EntityNotFoundException::new);
            BusinessService businessService = businessServiceMapper.toEntity(obj);
            existed.setDescription(businessService.getDescription());
            existed.setPrice(businessService.getPrice());
            existed.setName(businessService.getName());
            BusinessService save = businessServiceRepository.save(businessService);
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
        Optional<BusinessService> optionalBusinessService = businessServiceRepository.findById(id);
        BusinessService existed = optionalBusinessService.orElseThrow(EntityNotFoundException::new);
        return businessServiceMapper.toDto(optionalBusinessService.get());
    }

}
