package org.booking.core.repository;

import org.booking.core.domain.entity.employee.history.EmployeeReservationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeReservationHistoryRepository extends JpaRepository<EmployeeReservationHistory, Long> {

    Optional<EmployeeReservationHistory> findByEmployeeId(Long employeeId);
}
