package org.booking.core.domain.entity.business;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.booking.core.domain.entity.base.AbstractEntity;

import java.math.BigDecimal;

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

}
