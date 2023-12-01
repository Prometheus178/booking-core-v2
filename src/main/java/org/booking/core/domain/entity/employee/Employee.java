package org.booking.core.domain.entity.employee;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.booking.core.domain.entity.base.User;

@Entity(name = Employee.ENTITY_NAME)
@Table(name = Employee.TABLE_NAME)
@Getter
@Setter
public class Employee extends User {
    public static final String TABLE_NAME = "employees";
    public static final String ENTITY_NAME = "EMPLOYEE";
}
