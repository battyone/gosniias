package ru.dz.gosniias.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * Класс для целей отображения представления понятия-термина на web-странице.
 *
 * @author vassaeve
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RuleDto implements Serializable {

    private static final long serialVersionUID = -3446644123527455672L;

    @ApiModelProperty(value = "ID правила")
    private Long id;

    @ApiModelProperty(value = "Тип правила. Допустимые значения [SCRIPT, SET_RULE, CHECK_RULE]. По умолчанию CHECK_RULE")
    private String typeOf;

    @ApiModelProperty(value = "Наименование правила")
    private String description;

    public RuleDto() {
    }

    public RuleDto(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getTypeOf() {
        return typeOf;
    }

    public void setTypeOf(String typeOf) {
        this.typeOf = typeOf;
    }

    public Long getId() {
        return id;
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
