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
 * A Checkout.
 */
@Entity
@Table(name = "checkout")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Checkout implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fecha_salida", nullable = false)
    private LocalDate fechaSalida;

    @NotNull
    @Column(name = "peso", nullable = false)
    private Integer peso;

    @ManyToMany
    @JoinTable(
        name = "rel_checkout__al_sal",
        joinColumns = @JoinColumn(name = "checkout_id"),
        inverseJoinColumns = @JoinColumn(name = "al_sal_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tupper", "benef", "alEnt", "checkouts" }, allowSetters = true)
    private Set<AlSal> alSals = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "alSals", "pBenefs", "checkouts", "intols", "nucleo" }, allowSetters = true)
    private Benef benef;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Checkout id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaSalida() {
        return this.fechaSalida;
    }

    public Checkout fechaSalida(LocalDate fechaSalida) {
        this.setFechaSalida(fechaSalida);
        return this;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Integer getPeso() {
        return this.peso;
    }

    public Checkout peso(Integer peso) {
        this.setPeso(peso);
        return this;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Set<AlSal> getAlSals() {
        return this.alSals;
    }

    public void setAlSals(Set<AlSal> alSals) {
        this.alSals = alSals;
    }

    public Checkout alSals(Set<AlSal> alSals) {
        this.setAlSals(alSals);
        return this;
    }

    public Checkout addAlSal(AlSal alSal) {
        this.alSals.add(alSal);
        alSal.getCheckouts().add(this);
        return this;
    }

    public Checkout removeAlSal(AlSal alSal) {
        this.alSals.remove(alSal);
        alSal.getCheckouts().remove(this);
        return this;
    }

    public Benef getBenef() {
        return this.benef;
    }

    public void setBenef(Benef benef) {
        this.benef = benef;
    }

    public Checkout benef(Benef benef) {
        this.setBenef(benef);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Checkout)) {
            return false;
        }
        return id != null && id.equals(((Checkout) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Checkout{" +
            "id=" + getId() +
            ", fechaSalida='" + getFechaSalida() + "'" +
            ", peso=" + getPeso() +
            "}";
    }
}
