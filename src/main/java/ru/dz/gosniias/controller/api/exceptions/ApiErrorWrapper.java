package ru.dz.gosniias.controller.api.exceptions;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import org.apache.commons.lang.exception.ExceptionUtils;

/**
 *
 * @author vassaeve
 */
@ApiModel
public class ApiErrorWrapper implements Serializable {

    private static final long serialVersionUID = 3172131524314667554L;

    @ApiModelProperty(value = "true - если есть ошибки")
    private Boolean error;

    @ApiModelProperty(value = "Код ошибки")
    private ApiErrorCodes code;

    @ApiModelProperty(value = "StackTrace")
    private String stacktrace;

    @ApiModelProperty(value = "Сообщение ошибки")
    private String message;

    public ApiErrorWrapper(String message) {
        this.message = message;
        this.error = true;
    }

    public ApiErrorWrapper(String message, ApiErrorCodes code) {
        this.code = code;
        this.message = message;
        this.error = true;
    }

    public ApiErrorWrapper(String message, ApiErrorCodes code, Throwable e) {
        this.code = code;
        this.message = message;
        this.error = true;
        this.stacktrace = ExceptionUtils.getFullStackTrace(e);
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public ApiErrorCodes getCode() {
        return code;
    }

    public void setCode(ApiErrorCodes code) {
        this.code = code;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
