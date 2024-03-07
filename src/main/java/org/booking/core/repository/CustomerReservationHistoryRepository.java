package org.booking.core.repository;

import org.booking.core.domain.entity.user.history.UserReservationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerReservationHistoryRepository extends JpaRepository<UserReservationHistory, Long> {

    Optional<UserReservationHistory> findByCustomerId(Long customerId);
}
