package org.booking.core.domain.entity.user.history;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.booking.core.domain.entity.reservation.Reservation;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = UserReservationHistory.ENTITY_NAME)
@Table(name = UserReservationHistory.TABLE_NAME)
public class UserReservationHistory extends AbstractEntity {

	public static final String ENTITY_NAME = "UserReservationHistory";
	public static final String TABLE_NAME = "user_reservation_history";

	private String userEmail;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_reservation_history_reservations",
			joinColumns = {@JoinColumn(name = "user_reservation_history_id")},
			inverseJoinColumns = {@JoinColumn(name = "reservation_id")}
	)
	private Set<Reservation> reservations = new HashSet<>();


	public void addReservation(Reservation reservation) {
		this.reservations.add(reservation);
	}
}