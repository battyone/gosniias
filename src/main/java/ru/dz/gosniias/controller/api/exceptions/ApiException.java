package ru.dz.gosniias.controller.api.exceptions;

/**
 *
 * @author vassaeve
 */
public class ApiException extends Exception {

    private static final long serialVersionUID = 9088315464531809599L;
    private ApiErrorCodes code;

    public ApiException(String message, ApiErrorCodes code) {
        super(message);
        this.code = code;
    }

    public ApiErrorCodes getCode() {
        return code;
    }

    public void setCode(ApiErrorCodes code) {
        this.code = code;
    }
}
