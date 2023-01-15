package com.refood.trazabilidad.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Intol.
 */
@Entity
@Table(name = "intol")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Intol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany(mappedBy = "intols")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alSals", "pBenefs", "checkouts", "intols", "nucleo" }, allowSetters = true)
    private Set<Benef> benefs = new HashSet<>();

    @ManyToMany(mappedBy = "intols")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "intols", "benef" }, allowSetters = true)
    private Set<PBenef> pBenefs = new HashSet<>();

    @ManyToMany(mappedBy = "intols")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alEnts", "intols" }, allowSetters = true)
    private Set<TipoAl> tipoAls = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Intol id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Intol nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Intol descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Benef> getBenefs() {
        return this.benefs;
    }

    public void setBenefs(Set<Benef> benefs) {
        if (this.benefs != null) {
            this.benefs.forEach(i -> i.removeIntol(this));
        }
        if (benefs != null) {
            benefs.forEach(i -> i.addIntol(this));
        }
        this.benefs = benefs;
    }

    public Intol benefs(Set<Benef> benefs) {
        this.setBenefs(benefs);
        return this;
    }

    public Intol addBenef(Benef benef) {
        this.benefs.add(benef);
        benef.getIntols().add(this);
        return this;
    }

    public Intol removeBenef(Benef benef) {
        this.benefs.remove(benef);
        benef.getIntols().remove(this);
        return this;
    }

    public Set<PBenef> getPBenefs() {
        return this.pBenefs;
    }

    public void setPBenefs(Set<PBenef> pBenefs) {
        if (this.pBenefs != null) {
            this.pBenefs.forEach(i -> i.removeIntol(this));
        }
        if (pBenefs != null) {
            pBenefs.forEach(i -> i.addIntol(this));
        }
        this.pBenefs = pBenefs;
    }

    public Intol pBenefs(Set<PBenef> pBenefs) {
        this.setPBenefs(pBenefs);
        return this;
    }

    public Intol addPBenef(PBenef pBenef) {
        this.pBenefs.add(pBenef);
        pBenef.getIntols().add(this);
        return this;
    }

    public Intol removePBenef(PBenef pBenef) {
        this.pBenefs.remove(pBenef);
        pBenef.getIntols().remove(this);
        return this;
    }

    public Set<TipoAl> getTipoAls() {
        return this.tipoAls;
    }

    public void setTipoAls(Set<TipoAl> tipoAls) {
        if (this.tipoAls != null) {
            this.tipoAls.forEach(i -> i.removeIntol(this));
        }
        if (tipoAls != null) {
            tipoAls.forEach(i -> i.addIntol(this));
        }
        this.tipoAls = tipoAls;
    }

    public Intol tipoAls(Set<TipoAl> tipoAls) {
        this.setTipoAls(tipoAls);
        return this;
    }

    public Intol addTipoAl(TipoAl tipoAl) {
        this.tipoAls.add(tipoAl);
        tipoAl.getIntols().add(this);
        return this;
    }

    public Intol removeTipoAl(TipoAl tipoAl) {
        this.tipoAls.remove(tipoAl);
        tipoAl.getIntols().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Intol)) {
            return false;
        }
        return id != null && id.equals(((Intol) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Intol{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
