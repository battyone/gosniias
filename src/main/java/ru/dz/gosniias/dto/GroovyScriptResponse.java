package ru.dz.gosniias.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vassaeve
 */
public class GroovyScriptResponse implements Serializable {

    private static final long serialVersionUID = -5481599461107236176L;

    private List<String> errors;
    private boolean success;
    private String data;

    public GroovyScriptResponse() {
        data = "";
        errors = new ArrayList<>(0);
        success = true;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors.clear();
        this.errors.addAll(errors);
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setError(String oneError) {
        errors.add(oneError);
    }

}
