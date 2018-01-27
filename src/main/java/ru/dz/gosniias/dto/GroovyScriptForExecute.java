package ru.dz.gosniias.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.io.Serializable;

/**
 *
 * @author vassaeve
 */
@ApiModel
public class GroovyScriptForExecute implements Serializable {

    private static final long serialVersionUID = -916203813591312499L;

    @ApiModelProperty(value = "Тело groovy-скрипта", name = "body")
    @ApiParam(value = "Тело groovy-скрипта.", required = true)
    private String body;

    @ApiModelProperty(value = "Значения менованных параметров для передачи groovy-скрипту.", name = "params")
    private String params;

    public GroovyScriptForExecute() {
    }

    public GroovyScriptForExecute(String body, String mapka) {
        this.body = body;
        this.params = mapka;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

}
