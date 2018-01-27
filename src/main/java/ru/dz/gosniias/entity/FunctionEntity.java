package ru.dz.gosniias.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vassaeve
 */
@Entity
@Table(name = "FUNCTIONS")
@XmlRootElement
public class FunctionEntity implements Serializable {

    private static final long serialVersionUID = 5332217501492112771L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "request_names")
    private String requestNames;

    /**
     * void, action, function
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type_of")
    private FunctionTypeOf typeOf;

    @Column(name = "BODY")
    private String body;

    public FunctionEntity() {
    }

    public FunctionEntity(Long id) {
        this.id = id;
    }

    public String getRequestNames() {
        return requestNames;
    }

    public void setRequestNames(String requestNames) {
        this.requestNames = requestNames;
    }

    public FunctionTypeOf getTypeOf() {
        return typeOf;
    }

    public void setTypeOf(FunctionTypeOf typeOf) {
        this.typeOf = typeOf;
    }

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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
        if (!(object instanceof FunctionEntity)) {
            return false;
        }
        FunctionEntity other = (FunctionEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FunctionsEntity[ id=" + id + ", description=" + description + " ]";
    }

}
