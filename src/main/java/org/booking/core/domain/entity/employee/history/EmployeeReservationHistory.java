package org.booking.core.domain.entity.employee.history;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.booking.core.domain.entity.employee.Employee;
import org.booking.core.domain.entity.reservation.Reservation;

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
    @JoinTable(name = "employee_reservation_history_reservations", joinColumns = { @JoinColumn(name =
            "employee_reservation_history_id") }, inverseJoinColumns = { @JoinColumn(name = "reservation_id") })
    private Set<Reservation> reservations = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }
}