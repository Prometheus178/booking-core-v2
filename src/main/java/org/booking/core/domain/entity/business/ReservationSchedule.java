package org.booking.core.domain.entity.business;

import lombok.Getter;
import lombok.Setter;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.booking.core.domain.entity.reservation.Reservation;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity(name = ReservationSchedule.ENTITY_NAME)
@Table(name = ReservationSchedule.TABLE_NAME)
@Getter
@Setter
public class ReservationSchedule extends AbstractEntity {

    public static final String TABLE_NAME = "reservation_schedule";
    public static final String ENTITY_NAME = "BUSINESS_SERVICE";

    @ManyToMany(
            mappedBy = "reservationSchedule",
            fetch = FetchType.LAZY
    )
    private Business business;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "reservation_schedule_reservations_mapping",
            joinColumns = {@JoinColumn(name = "reservation_schedule_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "reservation_id", referencedColumnName = "id")})
    @MapKey(name = "date")
    private Map<LocalDate, Set<Reservation>> reservations = new HashMap<>();

    public Set<Reservation> getReservationsByDate(LocalDate date){
        return reservations.get(date);
    }

}
