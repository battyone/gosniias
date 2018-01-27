package ru.dz.gosniias.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author vassaeve
 */
@Entity
@Table(name = "PROJECTS")
public class ProjectEntity implements Serializable {

    private static final long serialVersionUID = -4870549727915035110L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

//    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
//    private List<TaxonEntity> taxons;
//    
    public ProjectEntity() {
//        taxons = new ArrayList<>(0);
    }

    public ProjectEntity(String description) {
        this.description = description;
    }

//    public List<TaxonEntity> getTaxons() {
//        return taxons;
//    }
//
//    public void setTaxons(List<TaxonEntity> taxons) {
//        this.taxons.clear();
//        this.taxons.addAll(taxons);
//    }

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
