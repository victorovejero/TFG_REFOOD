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
 * A AlimentoDeSalida.
 */
@Entity
@Table(name = "alimento_de_salida")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlimentoDeSalida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fecha_salida", nullable = false)
    private LocalDate fechaSalida;

    @ManyToOne
    @JsonIgnoreProperties(value = { "alimentoDeSalidas", "alimentoDeEntradas" }, allowSetters = true)
    private Tupper tupper;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "alimentoDeSalidas", "personaBeneficiarias", "checkouts", "intolerancias", "nucleo" },
        allowSetters = true
    )
    private Beneficiario beneficiario;

    @ManyToOne
    @JsonIgnoreProperties(value = { "alimentoDeSalidas", "frutaYVerduras", "tupper", "donante", "tipoDeAlimento" }, allowSetters = true)
    private AlimentoDeEntrada alimentoDeEntrada;

    @ManyToMany(mappedBy = "alimentoDeSalidas")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alimentoDeSalidas", "beneficiario" }, allowSetters = true)
    private Set<Checkout> checkouts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AlimentoDeSalida id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaSalida() {
        return this.fechaSalida;
    }

    public AlimentoDeSalida fechaSalida(LocalDate fechaSalida) {
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

    public AlimentoDeSalida tupper(Tupper tupper) {
        this.setTupper(tupper);
        return this;
    }

    public Beneficiario getBeneficiario() {
        return this.beneficiario;
    }

    public void setBeneficiario(Beneficiario beneficiario) {
        this.beneficiario = beneficiario;
    }

    public AlimentoDeSalida beneficiario(Beneficiario beneficiario) {
        this.setBeneficiario(beneficiario);
        return this;
    }

    public AlimentoDeEntrada getAlimentoDeEntrada() {
        return this.alimentoDeEntrada;
    }

    public void setAlimentoDeEntrada(AlimentoDeEntrada alimentoDeEntrada) {
        this.alimentoDeEntrada = alimentoDeEntrada;
    }

    public AlimentoDeSalida alimentoDeEntrada(AlimentoDeEntrada alimentoDeEntrada) {
        this.setAlimentoDeEntrada(alimentoDeEntrada);
        return this;
    }

    public Set<Checkout> getCheckouts() {
        return this.checkouts;
    }

    public void setCheckouts(Set<Checkout> checkouts) {
        if (this.checkouts != null) {
            this.checkouts.forEach(i -> i.removeAlimentoDeSalida(this));
        }
        if (checkouts != null) {
            checkouts.forEach(i -> i.addAlimentoDeSalida(this));
        }
        this.checkouts = checkouts;
    }

    public AlimentoDeSalida checkouts(Set<Checkout> checkouts) {
        this.setCheckouts(checkouts);
        return this;
    }

    public AlimentoDeSalida addCheckout(Checkout checkout) {
        this.checkouts.add(checkout);
        checkout.getAlimentoDeSalidas().add(this);
        return this;
    }

    public AlimentoDeSalida removeCheckout(Checkout checkout) {
        this.checkouts.remove(checkout);
        checkout.getAlimentoDeSalidas().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlimentoDeSalida)) {
            return false;
        }
        return id != null && id.equals(((AlimentoDeSalida) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlimentoDeSalida{" +
            "id=" + getId() +
            ", fechaSalida='" + getFechaSalida() + "'" +
            "}";
    }
}
