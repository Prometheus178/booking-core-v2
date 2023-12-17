package org.booking.core.service.business;

import org.booking.core.domain.dto.BusinessDto;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.mapper.BusinessMapper;
import org.booking.core.repository.BusinessRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessControllerBean implements BusinessController {

    private final BusinessRepository businessRepository;
    private final BusinessMapper businessMapper;

    public BusinessControllerBean(BusinessRepository businessRepository, BusinessMapper businessMapper) {
        this.businessRepository = businessRepository;
        this.businessMapper = businessMapper;
    }

    @Override
    public BusinessDto create(BusinessDto obj) {
        Business business = businessMapper.dtoTo(obj);
        Business saved = businessRepository.save(business);
        return businessMapper.toDto(saved);
    }

    @Override
    public BusinessDto update(Long aLong, BusinessDto obj) {
        Optional<Business> optionalBusiness = businessRepository.findById(aLong);
        if (optionalBusiness.isPresent()) {
            Business existed = optionalBusiness.get();
            Business business = businessMapper.dtoTo(obj);
            existed.setType(business.getType());
            existed.setDescription(business.getDescription());
            existed.setName(business.getName());
            existed.setAddress(business.getAddress());
            Business saved = businessRepository.save(existed);
            return businessMapper.toDto(saved);
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
    public BusinessDto getById(Long aLong) {
        Optional<Business> optionalBusiness = businessRepository.findById(aLong);
        if (optionalBusiness.isPresent()) {
            return businessMapper.toDto(optionalBusiness.get());
        }
        return null;
    }

    @Override
    public List<BusinessDto> getAll() {
        List<Business> all = businessRepository.findAll();
        if (!all.isEmpty()) {
            List<BusinessDto> allUsers = new ArrayList<>();
            all.forEach(business -> {
                allUsers.add(businessMapper.toDto(business));
            });
            return allUsers;
        }
        return null;
    }
}
