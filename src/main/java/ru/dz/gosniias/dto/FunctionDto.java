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
public class FunctionDto implements Serializable {

    private static final long serialVersionUID = -8984453000112018231L;

    @ApiModelProperty(value = "ID groovy-скрипта", name = "id")
    private Long id;

    @ApiModelProperty(value = "Наименование groovy-скрипта")
    private String description;

    @ApiModelProperty(value = "Тип groovy-скрипта. Возможные значения [FUNCTION, VOID, ACTION]. По умолчанию VOID")
    private String typeOf;

    @ApiModelProperty(value = "Именованные параметры, разделенные запятой")
    private String requestNames;

    public FunctionDto() {
    }

    public FunctionDto(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public FunctionDto(Long id, String description, String typeOf) {
        this.id = id;
        this.description = description;
        this.typeOf = typeOf;
    }

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
