package org.booking.core.domain.entity.customer;

import lombok.Getter;
import lombok.Setter;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.booking.core.domain.entity.reservation.Reservation;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = CustomerBookingHistory.ENTITY_NAME)
@Table(name = CustomerBookingHistory.TABLE_NAME)
public class CustomerBookingHistory extends AbstractEntity  {
    public static final String ENTITY_NAME = "ReservationHistory";
    public static final String TABLE_NAME = "customer_booking_history";

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated(value = EnumType.STRING)
    private EventType eventType;
    private LocalDateTime eventTime;


    private String details;

}