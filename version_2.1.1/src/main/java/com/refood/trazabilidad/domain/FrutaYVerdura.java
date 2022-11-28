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
 * A FrutaYVerdura.
 */
@Entity
@Table(name = "fruta_y_verdura")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FrutaYVerdura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre_alimento", nullable = false)
    private String nombreAlimento;

    @ManyToMany(mappedBy = "frutaYVerduras")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alimentoDeSalidas", "frutaYVerduras", "tupper", "donante", "tipoDeAlimento" }, allowSetters = true)
    private Set<AlimentoDeEntrada> alimentoDeEntradas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FrutaYVerdura id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreAlimento() {
        return this.nombreAlimento;
    }

    public FrutaYVerdura nombreAlimento(String nombreAlimento) {
        this.setNombreAlimento(nombreAlimento);
        return this;
    }

    public void setNombreAlimento(String nombreAlimento) {
        this.nombreAlimento = nombreAlimento;
    }

    public Set<AlimentoDeEntrada> getAlimentoDeEntradas() {
        return this.alimentoDeEntradas;
    }

    public void setAlimentoDeEntradas(Set<AlimentoDeEntrada> alimentoDeEntradas) {
        if (this.alimentoDeEntradas != null) {
            this.alimentoDeEntradas.forEach(i -> i.removeFrutaYVerdura(this));
        }
        if (alimentoDeEntradas != null) {
            alimentoDeEntradas.forEach(i -> i.addFrutaYVerdura(this));
        }
        this.alimentoDeEntradas = alimentoDeEntradas;
    }

    public FrutaYVerdura alimentoDeEntradas(Set<AlimentoDeEntrada> alimentoDeEntradas) {
        this.setAlimentoDeEntradas(alimentoDeEntradas);
        return this;
    }

    public FrutaYVerdura addAlimentoDeEntrada(AlimentoDeEntrada alimentoDeEntrada) {
        this.alimentoDeEntradas.add(alimentoDeEntrada);
        alimentoDeEntrada.getFrutaYVerduras().add(this);
        return this;
    }

    public FrutaYVerdura removeAlimentoDeEntrada(AlimentoDeEntrada alimentoDeEntrada) {
        this.alimentoDeEntradas.remove(alimentoDeEntrada);
        alimentoDeEntrada.getFrutaYVerduras().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FrutaYVerdura)) {
            return false;
        }
        return id != null && id.equals(((FrutaYVerdura) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FrutaYVerdura{" +
            "id=" + getId() +
            ", nombreAlimento='" + getNombreAlimento() + "'" +
            "}";
    }
}
