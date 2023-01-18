package com.refood.trazabilidad.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AlSal.
 */
@Entity
@Table(name = "al_sal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlSal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fecha_salida", nullable = false)
    private LocalDate fechaSalida;

    @ManyToOne
    @JsonIgnoreProperties(value = { "alSals", "alEnts" }, allowSetters = true)
    private Tupper tupper;

    @ManyToOne
    @JsonIgnoreProperties(value = { "alSals", "pBenefs", "checkouts", "intols", "nucleo" }, allowSetters = true)
    private Benef benef;

    @ManyToOne
    @JsonIgnoreProperties(value = { "alSals", "tupper", "donante", "tipoAl" }, allowSetters = true)
    private AlEnt alEnt;

    @ManyToMany(mappedBy = "alSals")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alSals", "benef" }, allowSetters = true)
    private Set<Checkout> checkouts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AlSal id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaSalida() {
        return this.fechaSalida;
    }

    public AlSal fechaSalida(LocalDate fechaSalida) {
        this.setFechaSalida(fechaSalida);
        return this;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Tupper getTupper() {
        return this.tupper;
    }

    public void setTupper(Tupper tupper) {
        this.tupper = tupper;
    }

    public AlSal tupper(Tupper tupper) {
        this.setTupper(tupper);
        return this;
    }

    public Benef getBenef() {
        return this.benef;
    }

    public void setBenef(Benef benef) {
        this.benef = benef;
    }

    public AlSal benef(Benef benef) {
        this.setBenef(benef);
        return this;
    }

    public AlEnt getAlEnt() {
        return this.alEnt;
    }

    public void setAlEnt(AlEnt alEnt) {
        this.alEnt = alEnt;
    }

    public AlSal alEnt(AlEnt alEnt) {
        this.setAlEnt(alEnt);
        return this;
    }

    public Set<Checkout> getCheckouts() {
        return this.checkouts;
    }

    public void setCheckouts(Set<Checkout> checkouts) {
        if (this.checkouts != null) {
            this.checkouts.forEach(i -> i.removeAlSal(this));
        }
        if (checkouts != null) {
            checkouts.forEach(i -> i.addAlSal(this));
        }
        this.checkouts = checkouts;
    }

    public AlSal checkouts(Set<Checkout> checkouts) {
        this.setCheckouts(checkouts);
        return this;
    }

    public AlSal addCheckout(Checkout checkout) {
        this.checkouts.add(checkout);
        checkout.getAlSals().add(this);
        return this;
    }

    public AlSal removeCheckout(Checkout checkout) {
        this.checkouts.remove(checkout);
        checkout.getAlSals().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlSal)) {
            return false;
        }
        return id != null && id.equals(((AlSal) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlSal{" +
            "id=" + getId() +
            ", fechaSalida='" + getFechaSalida() + "'" +
            "}";
    }
}
