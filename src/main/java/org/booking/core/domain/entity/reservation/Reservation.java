package org.booking.core.domain.entity.reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.booking.core.domain.entity.base.AbstractEntity;

import java.util.Date;

@Entity(name = Reservation.ENTITY_NAME)
@Table(name = Reservation.TABLE_NAME)
@Getter
@Setter
public class Reservation extends AbstractEntity {
    public static final String TABLE_NAME = "reservations";
    public static final String ENTITY_NAME = "RESERVATIONS";

//    private Business business;
//    private Service service;
    private Date startDate;
    private Date endDate;
//    private Customer customer;
    private State state;
}
