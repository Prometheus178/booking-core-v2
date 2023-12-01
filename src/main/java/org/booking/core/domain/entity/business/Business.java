package org.booking.core.domain.entity.business;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.booking.core.domain.entity.base.BaseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Entity(name = Business.ENTITY_NAME)
@Table(name = Business.TABLE_NAME)
@Getter
@Setter
public class Business extends BaseEntity {
    public static final String TABLE_NAME = "business";
    public static final String ENTITY_NAME = "BUSINESS";
    private Type type;
    private String address;
    private Map<String, Service> services = new HashMap<String, Service>();
}
