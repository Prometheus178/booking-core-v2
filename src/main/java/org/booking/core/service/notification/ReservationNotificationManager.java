package org.booking.core.service.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.booking.core.domain.dto.notification.DefaultNotificationDto;
import org.booking.core.domain.dto.notification.MetaInfoDto;
import org.booking.core.domain.entity.reservation.Reservation;
import org.springframework.stereotype.Service;

@Log
@RequiredArgsConstructor
@Service
public class ReservationNotificationManager implements NotificationManager<Reservation> {

	private final NotificationService notificationService;
	private final NotificationDataProvider<Reservation, DefaultNotificationDto> dataProvider;

	@Override
	public void sendNotification(String receiver, String action, Reservation obj) {
		DefaultNotificationDto message = dataProvider.generateMessage(action, obj);
		message.setMetaInfo(getMetaInfoDto());
		notificationService.sent(receiver, toJson(message));
	}

	private MetaInfoDto getMetaInfoDto() {
		MetaInfoDto metaInfoDto = new MetaInfoDto();
		metaInfoDto.setSender("booking-core");
		metaInfoDto.setReceiver("booking-core-notification-service");
		return metaInfoDto;
	}
}
