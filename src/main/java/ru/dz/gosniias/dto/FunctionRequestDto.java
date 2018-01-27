package ru.dz.gosniias.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 *
 * @author vassaeve
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FunctionRequestDto implements Serializable {

    private static final long serialVersionUID = 9154559567437402182L;

    @ApiModelProperty(value = "Наименование groovy-скрипта")
    private String description;

    @ApiModelProperty(value = "Тип groovy-скрипта. Возможные значения [FUNCTION, VOID, ACTION]. По умолчанию VOID")
    private String typeOf;

    @ApiModelProperty(value = "Именованные параметры, разделенные запятой")
    private String requestNames;

    public FunctionRequestDto() {
    }

    public String getTypeOf() {
        return typeOf;
    }

    public String getRequestNames() {
        return requestNames;
    }

    public void setRequestNames(String requestNames) {
        this.requestNames = requestNames;
    }

    public void setTypeOf(String typeOf) {
        this.typeOf = typeOf;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
