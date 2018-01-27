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
public class RelationDto implements Serializable {

    private static final long serialVersionUID = -8804134987069220719L;

    @ApiModelProperty(value = "ID факта")
    private long id;

    @ApiModelProperty(value = "ID термина-объекта")
    private long parentId;

    @ApiModelProperty(value = "Наименование термина-объекта")
    private String parentName;

    @ApiModelProperty(value = "ID термина-субъекта")
    private long childId;

    @ApiModelProperty(value = "Наименование термина-субъекта")
    private String childName;
    
    @ApiModelProperty(value = "Наименование отношения (предикат)")
    private String relationName;

    public RelationDto() {
    }

    public long getChildId() {
        return childId;
    }

    public void setChildId(long childId) {
        this.childId = childId;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

}
