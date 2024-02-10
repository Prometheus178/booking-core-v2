package org.booking.core.domain.entity.employee.history;

import lombok.Getter;
import lombok.Setter;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.booking.core.domain.entity.customer.EventType;
import org.booking.core.domain.entity.employee.Employee;
import org.booking.core.domain.entity.reservation.Reservation;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = EmployeeReservationHistory.ENTITY_NAME)
@Table(name = EmployeeReservationHistory.TABLE_NAME)
public class EmployeeReservationHistory extends AbstractEntity {
    public static final String ENTITY_NAME = "EmployeeReservationHistory";
    public static final String TABLE_NAME = "employee_reservation_history";

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Set<Reservation> reservations = new HashSet<>();

    @OneToOne(mappedBy = "reservationHistory")
    private Employee employee;

    @Enumerated(value = EnumType.STRING)
    private EventType eventType;
    private LocalDateTime eventTime;
    private String details;

}