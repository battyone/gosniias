package ru.dz.gosniias.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vassaeve
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RuleResponse implements Serializable {

    private static final long serialVersionUID = -6130631734165377851L;

    @ApiModelProperty(value = "Список ошибок и исключений, возникших в ходе исполнения правила")
    private List<String> errors;
    
    @ApiModelProperty(value = "Флаг успешности исполнения правила")
    private boolean success;
    
    @ApiModelProperty(value = "Конъюнкция всех fact")
    private boolean total;
    
    @ApiModelProperty(value = "Ответ")
    private List<ResponseDetail> data;

    public RuleResponse() {
        data = new ArrayList<>(0);
        errors = new ArrayList<>(0);
        success = true;
        total = true;
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean isTotal() {
        return total;
    }

    public void setTotal(boolean total) {
        this.total = total;
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

    public List<ResponseDetail> getData() {
        return data;
    }

    public void setData(List<ResponseDetail> data) {
        this.data.clear();
        this.data.addAll(data);
    }

}
