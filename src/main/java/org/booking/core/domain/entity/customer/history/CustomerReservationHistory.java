package org.booking.core.domain.entity.customer.history;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.booking.core.domain.entity.customer.Customer;
import org.booking.core.domain.entity.reservation.Reservation;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = CustomerReservationHistory.ENTITY_NAME)
@Table(name = CustomerReservationHistory.TABLE_NAME)
public class CustomerReservationHistory extends AbstractEntity {
    public static final String ENTITY_NAME = "CustomerReservationHistory";
    public static final String TABLE_NAME = "customer_reservation_history";

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "customer_reservation_history_reservations", joinColumns = { @JoinColumn(name =
            "customer_reservation_history_id") }, inverseJoinColumns = { @JoinColumn(name = "reservation_id") })
    private Set<Reservation> reservations = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }
}