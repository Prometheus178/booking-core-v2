package org.booking.core.domain.entity.reservation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.booking.core.domain.entity.business.Business;
import org.booking.core.domain.entity.business.service.BusinessService;
import org.booking.core.domain.entity.customer.Customer;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@ToString
@Entity(name = Reservation.ENTITY_NAME)
@Table(name = Reservation.TABLE_NAME)
@Getter
@Setter
public class Reservation extends AbstractEntity {
    public static final String TABLE_NAME = "reservations";
    public static final String ENTITY_NAME = "RESERVATIONS";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_service_id")
    private BusinessService service;

    private LocalDateTime bookingTime;

    @Enumerated(value = EnumType.STRING)
    private State state;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Reservation that = (Reservation) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
