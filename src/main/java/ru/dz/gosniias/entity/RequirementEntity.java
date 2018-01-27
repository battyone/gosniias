package ru.dz.gosniias.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vassaeve
 */
@Entity
@Table(name = "Requirement")
@XmlRootElement
public class RequirementEntity implements Serializable {

    private static final long serialVersionUID = 517335247324656061L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "body")
    private String body;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of")
    private RequirementTypeOf typeOf;

    @ManyToOne
    private ProjectEntity project;

    public RequirementEntity() {
    }

    public RequirementTypeOf getTypeOf() {
        return typeOf;
    }

    public void setTypeOf(RequirementTypeOf typeOf) {
        this.typeOf = typeOf;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "RequirementEntity{" + "id=" + id + ", description=" + description + '}';
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
