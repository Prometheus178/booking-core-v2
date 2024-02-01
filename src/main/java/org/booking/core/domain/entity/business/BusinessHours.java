package org.booking.core.domain.entity.business;

import lombok.Getter;
import lombok.Setter;
import org.booking.core.domain.entity.base.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalTime;

@Getter
@Setter
@Entity(name = BusinessHours.ENTITY_NAME)
@Table(name = BusinessHours.TABLE_NAME)
public class BusinessHours extends AbstractEntity {
    public static final String ENTITY_NAME = "BusinessHours";
    public static final String TABLE_NAME = "business_hours";
    private LocalTime openTime;
    private LocalTime closeTime;
}