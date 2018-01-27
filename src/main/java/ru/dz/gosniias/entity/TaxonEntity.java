package ru.dz.gosniias.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vassaeve
 */
@Entity
@Table(name = "TAXON")
@XmlRootElement
public class TaxonEntity implements Serializable {

    private static final long serialVersionUID = 610877360942496116L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToMany(targetEntity = RelationEntity.class, mappedBy = "taxonParentId") //поудаляем ручками!, orphanRemoval = true, cascade = CascadeType.REMOVE
    private List<RelationEntity> relationsCollectionParent;

    @OneToMany(targetEntity = RelationEntity.class, mappedBy = "taxonChildId") //поудаляем ручками!, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RelationEntity> relationsCollectionChild;

    @ManyToOne
    private ProjectEntity project;

    public TaxonEntity() {
        relationsCollectionParent = new ArrayList<>(0);
        relationsCollectionChild = new ArrayList<>(0);
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "TaxonEntity{" + "id=" + id + ", description=" + description + '}';
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RelationEntity> getRelationsCollectionParent() {
        return relationsCollectionParent;
    }

    public void setRelationsCollectionParent(List<RelationEntity> relationsCollectionParent) {
        this.relationsCollectionParent.clear();
        this.relationsCollectionParent.addAll(relationsCollectionParent);
    }

    public List<RelationEntity> getRelationsCollectionChild() {
        return relationsCollectionChild;
    }

    public void setRelationsCollectionChild(List<RelationEntity> relationsCollectionChild) {
        this.relationsCollectionChild.clear();
        this.relationsCollectionChild.addAll(relationsCollectionChild);
    }

}
