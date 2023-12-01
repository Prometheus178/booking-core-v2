package org.booking.core.domain.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public abstract class User extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

}
