package org.booking.core.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.booking.core.domain.entity.user.User;

@Getter
@Setter
@Builder
@Entity(name = User.ENTITY_NAME)
@Table(name = User.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AbstractEntity {

    public static final String ENTITY_NAME = "Role";
    public static final String TABLE_NAME = "roles";

    private String name;
}
