package ru.dz.gosniias.controller.api.exceptions;

/**
 *
 * @author vassaeve
 */
public enum ApiErrorCodes {
    SCRIPT_NOT_FOUND("Скрипт не найден");

    ApiErrorCodes(String description) {
        this.description = description;
    }
    private final String description;

    public String getDescription() {
        return description;
    }

}
