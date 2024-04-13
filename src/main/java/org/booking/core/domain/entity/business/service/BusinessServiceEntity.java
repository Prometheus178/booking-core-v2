package org.booking.core.domain.entity.business.service;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.booking.core.domain.entity.business.Business;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = BusinessServiceEntity.ENTITY_NAME)
@Table(name = BusinessServiceEntity.TABLE_NAME)
@Getter
@Setter
public class BusinessServiceEntity extends AbstractEntity {

	public static final String TABLE_NAME = "business_services";
	public static final String ENTITY_NAME = "BusinessServiceEntity";

	private String name;
	private String description;
	private BigDecimal price;
	private int duration;
	private String modifiedByUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "business_id", referencedColumnName = "id")
	private Business business;

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		BusinessServiceEntity that = (BusinessServiceEntity) o;
		return getId() != null && Objects.equals(getId(), that.getId());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
	}
}
