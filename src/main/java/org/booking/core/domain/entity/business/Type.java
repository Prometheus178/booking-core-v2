package org.booking.core.domain.entity.business;

public enum Type {

	BARBERSHOP("BARBERSHOP");

	private final String name;

	Type(String name) {
		this.name = name;
	}

	public static Type getByName(String name) {
		return Type.valueOf(name);
	}

	public String getName() {
		return name;
	}
}
