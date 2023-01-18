package com.refood.trazabilidad.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AlEnt.
 */
@Entity
@Table(name = "al_ent")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlEnt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "peso", nullable = false)
    private Double peso;

    @NotNull
    @Column(name = "fruta_y_verdura", nullable = false)
    private Boolean frutaYVerdura;

    @NotNull
    @Column(name = "fecha_y_hora_entrada", nullable = false)
    private ZonedDateTime fechaYHoraEntrada;

    @Column(name = "fecha_y_hora_recogida")
    private ZonedDateTime fechaYHoraRecogida;

    @Column(name = "fecha_y_hora_preparacion")
    private ZonedDateTime fechaYHoraPreparacion;

    @OneToMany(mappedBy = "alEnt")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tupper", "benef", "alEnt", "checkouts" }, allowSetters = true)
    private Set<AlSal> alSals = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "alSals", "alEnts" }, allowSetters = true)
    private Tupper tupper;

    @ManyToOne
    @JsonIgnoreProperties(value = { "alEnts", "nucleo" }, allowSetters = true)
    private Donante donante;

    @ManyToOne
    @JsonIgnoreProperties(value = { "alEnts", "intols" }, allowSetters = true)
    private TipoAl tipoAl;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AlEnt id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPeso() {
        return this.peso;
    }

    public AlEnt peso(Double peso) {
        this.setPeso(peso);
        return this;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Boolean getFrutaYVerdura() {
        return this.frutaYVerdura;
    }

    public AlEnt frutaYVerdura(Boolean frutaYVerdura) {
        this.setFrutaYVerdura(frutaYVerdura);
        return this;
    }

    public void setFrutaYVerdura(Boolean frutaYVerdura) {
        this.frutaYVerdura = frutaYVerdura;
    }

    public ZonedDateTime getFechaYHoraEntrada() {
        return this.fechaYHoraEntrada;
    }

    public AlEnt fechaYHoraEntrada(ZonedDateTime fechaYHoraEntrada) {
        this.setFechaYHoraEntrada(fechaYHoraEntrada);
        return this;
    }

    public void setFechaYHoraEntrada(ZonedDateTime fechaYHoraEntrada) {
        this.fechaYHoraEntrada = fechaYHoraEntrada;
    }

    public ZonedDateTime getFechaYHoraRecogida() {
        return this.fechaYHoraRecogida;
    }

    public AlEnt fechaYHoraRecogida(ZonedDateTime fechaYHoraRecogida) {
        this.setFechaYHoraRecogida(fechaYHoraRecogida);
        return this;
    }

    public void setFechaYHoraRecogida(ZonedDateTime fechaYHoraRecogida) {
        this.fechaYHoraRecogida = fechaYHoraRecogida;
    }

    public ZonedDateTime getFechaYHoraPreparacion() {
        return this.fechaYHoraPreparacion;
    }

    public AlEnt fechaYHoraPreparacion(ZonedDateTime fechaYHoraPreparacion) {
        this.setFechaYHoraPreparacion(fechaYHoraPreparacion);
        return this;
    }

    public void setFechaYHoraPreparacion(ZonedDateTime fechaYHoraPreparacion) {
        this.fechaYHoraPreparacion = fechaYHoraPreparacion;
    }

    public Set<AlSal> getAlSals() {
        return this.alSals;
    }

    public void setAlSals(Set<AlSal> alSals) {
        if (this.alSals != null) {
            this.alSals.forEach(i -> i.setAlEnt(null));
        }
        if (alSals != null) {
            alSals.forEach(i -> i.setAlEnt(this));
        }
        this.alSals = alSals;
    }

    public AlEnt alSals(Set<AlSal> alSals) {
        this.setAlSals(alSals);
        return this;
    }

    public AlEnt addAlSal(AlSal alSal) {
        this.alSals.add(alSal);
        alSal.setAlEnt(this);
        return this;
    }

    public AlEnt removeAlSal(AlSal alSal) {
        this.alSals.remove(alSal);
        alSal.setAlEnt(null);
        return this;
    }

    public Tupper getTupper() {
        return this.tupper;
    }

    public void setTupper(Tupper tupper) {
        this.tupper = tupper;
    }

    public AlEnt tupper(Tupper tupper) {
        this.setTupper(tupper);
        return this;
    }

    public Donante getDonante() {
        return this.donante;
    }

    public void setDonante(Donante donante) {
        this.donante = donante;
    }

    public AlEnt donante(Donante donante) {
        this.setDonante(donante);
        return this;
    }

    public TipoAl getTipoAl() {
        return this.tipoAl;
    }

    public void setTipoAl(TipoAl tipoAl) {
        this.tipoAl = tipoAl;
    }

    public AlEnt tipoAl(TipoAl tipoAl) {
        this.setTipoAl(tipoAl);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlEnt)) {
            return false;
        }
        return id != null && id.equals(((AlEnt) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlEnt{" +
            "id=" + getId() +
            ", peso=" + getPeso() +
            ", frutaYVerdura='" + getFrutaYVerdura() + "'" +
            ", fechaYHoraEntrada='" + getFechaYHoraEntrada() + "'" +
            ", fechaYHoraRecogida='" + getFechaYHoraRecogida() + "'" +
            ", fechaYHoraPreparacion='" + getFechaYHoraPreparacion() + "'" +
            "}";
    }
}
