package ru.dz.gosniias.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vassaeve
 */
public class RelationsDto implements Serializable {

    private static final long serialVersionUID = 4112161451033412886L;

    private int size;
    private List<RelationDto> relations;

    public RelationsDto() {
        this.relations = new ArrayList<>(0);
    }

    public RelationsDto(List<RelationDto> list) {
        this.relations = new ArrayList<>(list);
    }

    public RelationsDto(int size, List<RelationDto> list) {
        this.size = size;
        this.relations = new ArrayList<>(list);
    }

    public List<RelationDto> getRelations() {
        return relations;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setRelations(List<RelationDto> projects) {
        this.relations.clear();
        this.relations.addAll(projects);
    }

}
