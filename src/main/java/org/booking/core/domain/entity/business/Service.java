package org.booking.core.domain.entity.business;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.booking.core.domain.entity.base.BaseEntity;

import java.math.BigDecimal;

@Entity(name = Service.ENTITY_NAME)
@Table(name = Service.TABLE_NAME)
@Getter
@Setter
public class Service extends BaseEntity {

    public static final String TABLE_NAME = "services";
    public static final String ENTITY_NAME = "SERVICE";

    private String name;
    private String description;
    private BigDecimal price;

}
