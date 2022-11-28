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
        name = "rel_checkout__alimento_de_salida",
        joinColumns = @JoinColumn(name = "checkout_id"),
        inverseJoinColumns = @JoinColumn(name = "alimento_de_salida_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tupper", "beneficiario", "alimentoDeEntrada", "checkouts" }, allowSetters = true)
    private Set<AlimentoDeSalida> alimentoDeSalidas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "alimentoDeSalidas", "personaBeneficiarias", "checkouts", "intolerancias", "nucleo" },
        allowSetters = true
    )
    private Beneficiario beneficiario;

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

    public Set<AlimentoDeSalida> getAlimentoDeSalidas() {
        return this.alimentoDeSalidas;
    }

    public void setAlimentoDeSalidas(Set<AlimentoDeSalida> alimentoDeSalidas) {
        this.alimentoDeSalidas = alimentoDeSalidas;
    }

    public Checkout alimentoDeSalidas(Set<AlimentoDeSalida> alimentoDeSalidas) {
        this.setAlimentoDeSalidas(alimentoDeSalidas);
        return this;
    }

    public Checkout addAlimentoDeSalida(AlimentoDeSalida alimentoDeSalida) {
        this.alimentoDeSalidas.add(alimentoDeSalida);
        alimentoDeSalida.getCheckouts().add(this);
        return this;
    }

    public Checkout removeAlimentoDeSalida(AlimentoDeSalida alimentoDeSalida) {
        this.alimentoDeSalidas.remove(alimentoDeSalida);
        alimentoDeSalida.getCheckouts().remove(this);
        return this;
    }

    public Beneficiario getBeneficiario() {
        return this.beneficiario;
    }

    public void setBeneficiario(Beneficiario beneficiario) {
        this.beneficiario = beneficiario;
    }

    public Checkout beneficiario(Beneficiario beneficiario) {
        this.setBeneficiario(beneficiario);
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
