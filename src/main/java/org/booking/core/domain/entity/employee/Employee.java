package org.booking.core.domain.entity.employee;

import jakarta.persistence.*;
import lombok.*;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.entity.customer.User;
import org.booking.core.domain.entity.employee.history.EmployeeReservationHistory;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = Employee.ENTITY_NAME)
@Table(name = Employee.TABLE_NAME)
public class Employee extends AbstractEntity {

    public static final String TABLE_NAME = "employees";
    public static final String ENTITY_NAME = "Employee";

    @OneToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "employee")
    private EmployeeReservationHistory reservationHistory;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employee_business",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "business_id")
    )
    private Set<Business> businesses = new HashSet<>();


    public void setBusinesses(Set<Business> businesses) {
        this.businesses.addAll(businesses);
    }

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
