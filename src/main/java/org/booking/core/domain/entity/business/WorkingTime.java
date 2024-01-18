package org.booking.core.domain.entity.business;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class WorkingTime implements Serializable {


    /**
     * 09:00 AM
     */
    private String open;

    /**
     * 06:00 PM
     */
    private String close;

}