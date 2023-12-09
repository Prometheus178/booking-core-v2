package org.booking.core.domain.entity.reservation;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.entity.business.BusinessService;
import org.booking.core.domain.entity.customer.Customer;

@Entity(name = Reservation.ENTITY_NAME)
@Table(name = Reservation.TABLE_NAME)
@Getter
@Setter
public class Reservation extends AbstractEntity {
    public static final String TABLE_NAME = "reservations";
    public static final String ENTITY_NAME = "RESERVATIONS";

    @Embedded
    Duration duration;
    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;

    @ManyToOne
    @JoinColumn(name = "business_service_id")
    private BusinessService service;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private State state;
}
