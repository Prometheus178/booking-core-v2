package org.booking.core.domain.entity.business;

import lombok.Getter;
import lombok.Setter;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.booking.core.domain.entity.reservation.Reservation;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name = ReservationSchedule.ENTITY_NAME)
@Table(name = ReservationSchedule.TABLE_NAME)
@Getter
@Setter
public class ReservationSchedule extends AbstractEntity {

    public static final String TABLE_NAME = "reservation_schedule";
    public static final String ENTITY_NAME = "ReservationSchedule";

    @OneToOne(mappedBy = "reservationSchedule")
    private Business business;

    private LocalDate date;

    @ElementCollection
    private Set<Reservation> reservations = new HashSet<>();

}
