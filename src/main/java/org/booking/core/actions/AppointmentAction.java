package org.booking.core.actions;

public enum AppointmentAction {

	CREATED_RESERVATION("notification.action.appointment.created.reservation"),
	MODIFIED_RESERVATION("notification.action.appointment.modified.reservation");

	private final String value;

	AppointmentAction(String value) {
		this.value = value;
	}

	public static AppointmentAction getByValue(String value) {
		return AppointmentAction.valueOf(value);
	}

	public String getValue() {
		return value;
	}


}
