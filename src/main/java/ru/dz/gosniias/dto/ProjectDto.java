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
public class ProjectDto implements Serializable {

    private static final long serialVersionUID = -735657931473902294L;

    @ApiModelProperty(value = "ID проекта")
    private Long id;

    @ApiModelProperty(value = "Наименование проекта")
    private String description;

    public ProjectDto() {
    }

    public ProjectDto(Long id, String description) {
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
