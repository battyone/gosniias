package ru.dz.gosniias.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author vassaeve
 */
@Entity
@Table(name = "relations")
public class RelationEntity implements Serializable {

    private static final long serialVersionUID = 9129108991855354757L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "relation_name")
    private String relationName;

    @JoinColumn(name = "taxon_parent_id", referencedColumnName = "id")
    @ManyToOne
    private TaxonEntity taxonParentId;

    @JoinColumn(name = "taxon_child_id", referencedColumnName = "id")
    @ManyToOne// поудаляем ручками! (cascade = CascadeType.REMOVE)
    private TaxonEntity taxonChildId;

//    @ManyToOne
//    private ProjectEntity project;

    public RelationEntity() {
    }

    public RelationEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaxonEntity getTaxonParentId() {
        return taxonParentId;
    }

    public void setTaxonParentId(TaxonEntity taxonParentId) {
        this.taxonParentId = taxonParentId;
    }

//    public ProjectEntity getProject() {
//        return project;
//    }
//
//    public void setProject(ProjectEntity project) {
//        this.project = project;
//    }

    public TaxonEntity getTaxonChildId() {
        return taxonChildId;
    }

    public void setTaxonChildId(TaxonEntity taxonChildId) {
        this.taxonChildId = taxonChildId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelationEntity)) {
            return false;
        }
        RelationEntity other = (RelationEntity) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ru.dz.gosniias.entity.Relations[ id=" + id + " ]";
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

}
