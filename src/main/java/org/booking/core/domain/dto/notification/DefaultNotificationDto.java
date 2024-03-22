package org.booking.core.domain.dto.notification;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
public class DefaultNotificationDto implements Serializable {

	String uuid;
	MetaInfoDto metaInfo;
	List<ContactDto> contacts;
	String action;

}
