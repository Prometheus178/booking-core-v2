package org.booking.core.domain.entity.business;

public enum Type {

    BARBERSHOP("BARBERSHOP");

    private String name;

    private Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Type getByName(String name) {
        return Type.valueOf(name);
    }
}
