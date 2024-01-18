package org.booking.core.domain.entity.business.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@ToString
@Entity(name = BusinessService.ENTITY_NAME)
@Table(name = BusinessService.TABLE_NAME)
@Getter
@Setter
public class BusinessService extends AbstractEntity {

    public static final String TABLE_NAME = "business_services";
    public static final String ENTITY_NAME = "BUSINESS_SERVICE";

    private String name;
    private String description;
    private BigDecimal price;
    private int durationTime;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        BusinessService that = (BusinessService) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
