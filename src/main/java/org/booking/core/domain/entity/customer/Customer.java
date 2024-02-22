package org.booking.core.domain.entity.customer;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.booking.core.domain.entity.base.User;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@ToString
@AllArgsConstructor
@Entity(name = Customer.ENTITY_NAME)
@Table(name = Customer.TABLE_NAME)
public class Customer extends User{

    public static final String TABLE_NAME = "customers";
    public static final String ENTITY_NAME = "CUSTOMER";

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Customer customer = (Customer) o;
        return getId() != null && Objects.equals(getId(), customer.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
