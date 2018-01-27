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
public class TaxonDto implements Serializable {

    private static final long serialVersionUID = 4581173511571714L;

    @ApiModelProperty(value = "ID термина")
    private Long id;

    @ApiModelProperty(value = "Наименование термина")
    private String description;

    public TaxonDto() {
    }

    public TaxonDto(Long id, String description) {
        this.id = id;
        this.description = description;
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
