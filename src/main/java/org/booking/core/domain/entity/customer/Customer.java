package org.booking.core.domain.entity.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.booking.core.domain.entity.base.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = Customer.ENTITY_NAME)
@Table(name = Customer.TABLE_NAME)
public class Customer extends User{

    public static final String TABLE_NAME = "customers";
    public static final String ENTITY_NAME = "CUSTOMER";

    @Override
    public Long getId() {
        return super.getId();
    }
}
