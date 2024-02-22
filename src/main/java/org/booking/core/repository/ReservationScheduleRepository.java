package org.booking.core.repository;

import org.booking.core.domain.entity.business.ReservationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationScheduleRepository extends JpaRepository<ReservationSchedule, Long> {

    Optional<ReservationSchedule> findByBusinessId(Long businessId);
}
