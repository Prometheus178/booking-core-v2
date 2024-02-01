package org.booking.core.domain.entity.business;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.booking.core.domain.entity.business.service.BusinessService;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@ToString
@Entity(name = Business.ENTITY_NAME)
@Table(name = Business.TABLE_NAME)
@Getter
@Setter
public class Business extends AbstractEntity {
    public static final String TABLE_NAME = "business";
    public static final String ENTITY_NAME = "BUSINESS";


    @Enumerated(EnumType.STRING)
    private Type type;
    private String name;
    private String address;
    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId
    private BusinessHours businessHours;

    @OneToMany(mappedBy = "business", fetch = FetchType.LAZY)
    private Set<BusinessService> businessServices = new HashSet<>();

    public void addBusinessService(BusinessService businessService) {
        businessServices.add(businessService);
    }

    public void removeBusinessService(BusinessService businessService) {
        businessServices.remove(businessService);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Business business = (Business) o;
        return getId() != null && Objects.equals(getId(), business.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
