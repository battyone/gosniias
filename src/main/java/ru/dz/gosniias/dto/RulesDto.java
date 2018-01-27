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
public class RulesDto implements Serializable {

    private static final long serialVersionUID = 8494879378873908335L;

    @ApiModelProperty(value = "Размер всего списка")
    private int size;
    
    @ApiModelProperty(dataType = "RuleDto", value = "список правил")
    private List<RuleDto> rules;

    public RulesDto() {
        this.rules = new ArrayList<>(0);
    }

    public RulesDto(List<RuleDto> list) {
        this.rules = new ArrayList<>(list);
    }

    public RulesDto(int size, List<RuleDto> list) {
        this.size = size;
        this.rules = new ArrayList<>(list);
    }

    public List<RuleDto> getRules() {
        return rules;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setRules(List<RuleDto> rules) {
        this.rules.clear();
        this.rules.addAll(rules);
    }

}
