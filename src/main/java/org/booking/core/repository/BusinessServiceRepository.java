package org.booking.core.repository;

import org.booking.core.domain.entity.business.service.BusinessServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessServiceRepository extends JpaRepository<BusinessServiceEntity, Long> {
}