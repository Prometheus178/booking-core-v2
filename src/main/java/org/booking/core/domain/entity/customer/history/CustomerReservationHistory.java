package org.booking.core.domain.entity.customer.history;

import lombok.Getter;
import lombok.Setter;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.booking.core.domain.entity.customer.Customer;
import org.booking.core.domain.entity.customer.EventType;
import org.booking.core.domain.entity.reservation.Reservation;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = CustomerReservationHistory.ENTITY_NAME)
@Table(name = CustomerReservationHistory.TABLE_NAME)
public class CustomerReservationHistory extends AbstractEntity {
    public static final String ENTITY_NAME = "CustomerBookingHistory";
    public static final String TABLE_NAME = "customer_reservation_history";

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Set<Reservation> reservations = new HashSet<>();

    @OneToOne(mappedBy = "reservationHistory")
    private Customer customer;

    @Enumerated(value = EnumType.STRING)
    private EventType eventType;
    private LocalDateTime eventTime;
    private String details;

}