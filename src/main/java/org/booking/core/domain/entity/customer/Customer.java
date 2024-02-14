package org.booking.core.domain.entity.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.booking.core.domain.entity.base.User;
import org.booking.core.domain.entity.customer.history.CustomerReservationHistory;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = Customer.ENTITY_NAME)
@Table(name = Customer.TABLE_NAME)
@NoArgsConstructor
public class Customer extends User {

    public static final String TABLE_NAME = "customers";
    public static final String ENTITY_NAME = "Customer";

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_history_id")
    private CustomerReservationHistory reservationHistory;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Customer customer = (Customer) object;
        return Objects.equals(reservationHistory, customer.reservationHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationHistory);
    }
}
