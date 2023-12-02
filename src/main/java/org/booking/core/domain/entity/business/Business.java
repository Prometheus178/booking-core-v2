package org.booking.core.domain.entity.business;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.booking.core.domain.entity.base.AbstractEntity;

import java.util.HashMap;
import java.util.Map;

@Entity(name = Business.ENTITY_NAME)
@Table(name = Business.TABLE_NAME)
@Getter
@Setter
public class Business extends AbstractEntity {
    public static final String TABLE_NAME = "business";
    public static final String ENTITY_NAME = "BUSINESS";
    private Type type;
    private String name;
    private String address;

    private String description;

    @ElementCollection
    @CollectionTable(name = "business_services_map", joinColumns = @JoinColumn(name = "business_id"))
    @MapKeyJoinColumn(name = "business_service_map_id")
    @Column(name = "service_name")
    private Map<String, BusinessService> businessServiceMap = new HashMap<>();
}
