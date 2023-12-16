package org.booking.core.domain.entity.business;

import lombok.Getter;
import lombok.Setter;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity(name = Business.ENTITY_NAME)
@Table(name = Business.TABLE_NAME)
@Getter
@Setter
public class Business extends AbstractEntity {
    public static final String TABLE_NAME = "business";
    public static final String ENTITY_NAME = "BUSINESS";

    @Enumerated
    private Type type;
    private String name;
    private String address;
    private String description;

//    @Basic(fetch = FetchType.LAZY)
//    @ElementCollection
//    @CollectionTable(name = "business_services_map", joinColumns = @JoinColumn(name = "business_id"))
//    @MapKeyJoinColumn(name = "business_service_map_id")
//    @Column(name = "business_service_map_id")
//    private Map<String, BusinessService> businessServiceMap;


}
