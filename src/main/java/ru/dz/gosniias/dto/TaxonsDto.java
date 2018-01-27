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
public class TaxonsDto implements Serializable {

    private static final long serialVersionUID = -1489205178920865835L;

    @ApiModelProperty(value = "Размер всего списка")
    private int size;

    @ApiModelProperty(value = "Список терминов")
    private List<TaxonDto> taxons;

    public TaxonsDto() {
        this.taxons = new ArrayList<>(0);
    }

    public TaxonsDto(List<TaxonDto> list) {
        this.taxons = new ArrayList<>(list);
    }

    public TaxonsDto(int size, List<TaxonDto> list) {
        this.size = size;
        this.taxons = new ArrayList<>(list);
    }

    public List<TaxonDto> getTaxons() {
        return taxons;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setTaxons(List<TaxonDto> taxons) {
        this.taxons.clear();
        this.taxons.addAll(taxons);
    }

}
