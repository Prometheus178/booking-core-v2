package org.booking.core.domain.entity.customer;

import lombok.*;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.booking.core.domain.entity.customer.history.CustomerReservationHistory;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = Customer.ENTITY_NAME)
@Table(name = Customer.TABLE_NAME)
public class Customer extends AbstractEntity {

    public static final String TABLE_NAME = "customers";
    public static final String ENTITY_NAME = "Customer";

    @OneToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
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
