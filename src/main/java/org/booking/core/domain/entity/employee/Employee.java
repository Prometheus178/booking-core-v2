package org.booking.core.domain.entity.employee;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.booking.core.domain.entity.base.User;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = Employee.ENTITY_NAME)
@Table(name = Employee.TABLE_NAME)
public class Employee extends User {

    public static final String TABLE_NAME = "employees";
    public static final String ENTITY_NAME = "EMPLOYEE";
}
