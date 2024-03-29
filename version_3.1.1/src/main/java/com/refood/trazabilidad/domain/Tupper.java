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
 * A Tupper.
 */
@Entity
@Table(name = "tupper")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tupper implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "peso", nullable = false)
    private Double peso;

    @NotNull
    @Column(name = "productor", nullable = false)
    private String productor;

    @NotNull
    @Column(name = "modelo", nullable = false)
    private String modelo;

    @NotNull
    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "tupper")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tupper", "benef", "alEnt", "checkouts" }, allowSetters = true)
    private Set<AlSal> alSals = new HashSet<>();

    @OneToMany(mappedBy = "tupper")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alSals", "tupper", "donante", "tipoAl" }, allowSetters = true)
    private Set<AlEnt> alEnts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tupper id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPeso() {
        return this.peso;
    }

    public Tupper peso(Double peso) {
        this.setPeso(peso);
        return this;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getProductor() {
        return this.productor;
    }

    public Tupper productor(String productor) {
        this.setProductor(productor);
        return this;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public String getModelo() {
        return this.modelo;
    }

    public Tupper modelo(String modelo) {
        this.setModelo(modelo);
        return this;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getPrecio() {
        return this.precio;
    }

    public Tupper precio(Double precio) {
        this.setPrecio(precio);
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Tupper descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<AlSal> getAlSals() {
        return this.alSals;
    }

    public void setAlSals(Set<AlSal> alSals) {
        if (this.alSals != null) {
            this.alSals.forEach(i -> i.setTupper(null));
        }
        if (alSals != null) {
            alSals.forEach(i -> i.setTupper(this));
        }
        this.alSals = alSals;
    }

    public Tupper alSals(Set<AlSal> alSals) {
        this.setAlSals(alSals);
        return this;
    }

    public Tupper addAlSal(AlSal alSal) {
        this.alSals.add(alSal);
        alSal.setTupper(this);
        return this;
    }

    public Tupper removeAlSal(AlSal alSal) {
        this.alSals.remove(alSal);
        alSal.setTupper(null);
        return this;
    }

    public Set<AlEnt> getAlEnts() {
        return this.alEnts;
    }

    public void setAlEnts(Set<AlEnt> alEnts) {
        if (this.alEnts != null) {
            this.alEnts.forEach(i -> i.setTupper(null));
        }
        if (alEnts != null) {
            alEnts.forEach(i -> i.setTupper(this));
        }
        this.alEnts = alEnts;
    }

    public Tupper alEnts(Set<AlEnt> alEnts) {
        this.setAlEnts(alEnts);
        return this;
    }

    public Tupper addAlEnt(AlEnt alEnt) {
        this.alEnts.add(alEnt);
        alEnt.setTupper(this);
        return this;
    }

    public Tupper removeAlEnt(AlEnt alEnt) {
        this.alEnts.remove(alEnt);
        alEnt.setTupper(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tupper)) {
            return false;
        }
        return id != null && id.equals(((Tupper) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tupper{" +
            "id=" + getId() +
            ", peso=" + getPeso() +
            ", productor='" + getProductor() + "'" +
            ", modelo='" + getModelo() + "'" +
            ", precio=" + getPrecio() +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
