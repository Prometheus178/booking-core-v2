package org.booking.core.service.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.booking.core.domain.dto.notification.ContactDto;
import org.booking.core.domain.dto.notification.DefaultNotificationDto;
import org.booking.core.domain.entity.reservation.Reservation;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log
@RequiredArgsConstructor
@Service
public class ReservationNotificationDataProvider implements NotificationDataProvider<Reservation, DefaultNotificationDto> {

	@Override
	public DefaultNotificationDto generateMessage(String action, @NonNull Reservation obj) {
		ContactDto employeeContact = new ContactDto();
		employeeContact.setEmail(obj.getEmployeeEmail());
//		employeeContact.setRole(RoleClassification.MANAGER.name());
		ContactDto customerContact = new ContactDto();
		customerContact.setEmail(obj.getCustomerEmail());
//		customerContact.setRole(RoleClassification.CUSTOMER.name());
		return DefaultNotificationDto.builder()
				.action(action)
				.uuid(UUID.randomUUID().toString())
				.contacts(List.of(employeeContact, customerContact))
				.build();
	}


}
