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
 * A TipoAl.
 */
@Entity
@Table(name = "tipo_al")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TipoAl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre_alimento", nullable = false)
    private String nombreAlimento;

    @NotNull
    @Column(name = "fruta_y_verdura", nullable = false)
    private Boolean frutaYVerdura;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "tipoAl")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alSals", "tupper", "donante", "tipoAl" }, allowSetters = true)
    private Set<AlEnt> alEnts = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_tipo_al__intol",
        joinColumns = @JoinColumn(name = "tipo_al_id"),
        inverseJoinColumns = @JoinColumn(name = "intol_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "benefs", "pBenefs", "tipoAls" }, allowSetters = true)
    private Set<Intol> intols = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TipoAl id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreAlimento() {
        return this.nombreAlimento;
    }

    public TipoAl nombreAlimento(String nombreAlimento) {
        this.setNombreAlimento(nombreAlimento);
        return this;
    }

    public void setNombreAlimento(String nombreAlimento) {
        this.nombreAlimento = nombreAlimento;
    }

    public Boolean getFrutaYVerdura() {
        return this.frutaYVerdura;
    }

    public TipoAl frutaYVerdura(Boolean frutaYVerdura) {
        this.setFrutaYVerdura(frutaYVerdura);
        return this;
    }

    public void setFrutaYVerdura(Boolean frutaYVerdura) {
        this.frutaYVerdura = frutaYVerdura;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public TipoAl descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<AlEnt> getAlEnts() {
        return this.alEnts;
    }

    public void setAlEnts(Set<AlEnt> alEnts) {
        if (this.alEnts != null) {
            this.alEnts.forEach(i -> i.setTipoAl(null));
        }
        if (alEnts != null) {
            alEnts.forEach(i -> i.setTipoAl(this));
        }
        this.alEnts = alEnts;
    }

    public TipoAl alEnts(Set<AlEnt> alEnts) {
        this.setAlEnts(alEnts);
        return this;
    }

    public TipoAl addAlEnt(AlEnt alEnt) {
        this.alEnts.add(alEnt);
        alEnt.setTipoAl(this);
        return this;
    }

    public TipoAl removeAlEnt(AlEnt alEnt) {
        this.alEnts.remove(alEnt);
        alEnt.setTipoAl(null);
        return this;
    }

    public Set<Intol> getIntols() {
        return this.intols;
    }

    public void setIntols(Set<Intol> intols) {
        this.intols = intols;
    }

    public TipoAl intols(Set<Intol> intols) {
        this.setIntols(intols);
        return this;
    }

    public TipoAl addIntol(Intol intol) {
        this.intols.add(intol);
        intol.getTipoAls().add(this);
        return this;
    }

    public TipoAl removeIntol(Intol intol) {
        this.intols.remove(intol);
        intol.getTipoAls().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoAl)) {
            return false;
        }
        return id != null && id.equals(((TipoAl) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoAl{" +
            "id=" + getId() +
            ", nombreAlimento='" + getNombreAlimento() + "'" +
            ", frutaYVerdura='" + getFrutaYVerdura() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
