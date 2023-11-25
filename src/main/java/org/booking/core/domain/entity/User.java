package org.booking.core.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name = User.ENTITY_NAME)
@Table(name = User.TABLE_NAME)
@Getter
@Setter
public class User extends BaseEntity {

    public static final String TABLE_NAME = "users";
    public static final String ENTITY_NAME = "USER";

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

}
