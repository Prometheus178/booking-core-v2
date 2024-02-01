package org.booking.core.repository;

import org.booking.core.domain.entity.customer.history.CustomerReservationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerReservationHistoryRepository extends JpaRepository<CustomerReservationHistory, Long> {
}
