package io.diana.calculaterate.enums;

public enum TurnoverDayType {
    UNLOAD("Выгрузка"),
    LOAD("Погрузка");

    private final String value;

    TurnoverDayType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
