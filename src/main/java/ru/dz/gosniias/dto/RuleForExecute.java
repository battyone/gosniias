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
public class RuleForExecute implements Serializable {

    private static final long serialVersionUID = 1132428421267755137L;

   @ApiModelProperty(value = "Содержательная часть правила. Если body пустой, то в качестве содержательной части будет использовано body правила, найденного по переданному id.")
    private String body;

    @ApiModelProperty(value = "ID правила")
    private Long id;

    public RuleForExecute() {
    }

    public RuleForExecute(String body) {
        this.body = body;
    }

    public RuleForExecute(Long id, String body) {
        this.id=id;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
