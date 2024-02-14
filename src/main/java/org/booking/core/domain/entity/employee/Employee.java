package org.booking.core.domain.entity.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.booking.core.domain.entity.base.User;
import org.booking.core.domain.entity.employee.history.EmployeeReservationHistory;

import javax.persistence.*;
import java.util.Objects;

@AllArgsConstructor
@Entity(name = Employee.ENTITY_NAME)
@Table(name = Employee.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
public class Employee extends User {

    public static final String TABLE_NAME = "employees";
    public static final String ENTITY_NAME = "Employee";

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_history_id")
    private EmployeeReservationHistory reservationHistory;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Employee employee = (Employee) object;
        return Objects.equals(reservationHistory, employee.reservationHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationHistory);
    }
}
