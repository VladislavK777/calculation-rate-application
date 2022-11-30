package io.diana.calculaterate.enums;

public enum CargoFlightType {
    FULL("ГРУЖ"),
    EMPTY("ПОР");

    private String value;

    public String getValue() {
        return value;
    }

    CargoFlightType(String value) {
        this.value = value;
    }
}
