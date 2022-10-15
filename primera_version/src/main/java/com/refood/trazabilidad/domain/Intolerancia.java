package com.refood.trazabilidad.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Intolerancia.
 */
@Entity
@Table(name = "intolerancia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Intolerancia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_intolerancia")
    private String nombreIntolerancia;

    @ManyToMany
    @JoinTable(
        name = "rel_intolerancia__tipo_de_alimento",
        joinColumns = @JoinColumn(name = "intolerancia_id"),
        inverseJoinColumns = @JoinColumn(name = "tipo_de_alimento_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alimentoDeEntradas", "alimentoDeSalidas", "intolerancias" }, allowSetters = true)
    private Set<TipoDeAlimento> tipoDeAlimentos = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_intolerancia__beneficiario",
        joinColumns = @JoinColumn(name = "intolerancia_id"),
        inverseJoinColumns = @JoinColumn(name = "beneficiario_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alimentoDeSalidas", "nucleo", "intolerancias" }, allowSetters = true)
    private Set<Beneficiario> beneficiarios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Intolerancia id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreIntolerancia() {
        return this.nombreIntolerancia;
    }

    public Intolerancia nombreIntolerancia(String nombreIntolerancia) {
        this.setNombreIntolerancia(nombreIntolerancia);
        return this;
    }

    public void setNombreIntolerancia(String nombreIntolerancia) {
        this.nombreIntolerancia = nombreIntolerancia;
    }

    public Set<TipoDeAlimento> getTipoDeAlimentos() {
        return this.tipoDeAlimentos;
    }

    public void setTipoDeAlimentos(Set<TipoDeAlimento> tipoDeAlimentos) {
        this.tipoDeAlimentos = tipoDeAlimentos;
    }

    public Intolerancia tipoDeAlimentos(Set<TipoDeAlimento> tipoDeAlimentos) {
        this.setTipoDeAlimentos(tipoDeAlimentos);
        return this;
    }

    public Intolerancia addTipoDeAlimento(TipoDeAlimento tipoDeAlimento) {
        this.tipoDeAlimentos.add(tipoDeAlimento);
        tipoDeAlimento.getIntolerancias().add(this);
        return this;
    }

    public Intolerancia removeTipoDeAlimento(TipoDeAlimento tipoDeAlimento) {
        this.tipoDeAlimentos.remove(tipoDeAlimento);
        tipoDeAlimento.getIntolerancias().remove(this);
        return this;
    }

    public Set<Beneficiario> getBeneficiarios() {
        return this.beneficiarios;
    }

    public void setBeneficiarios(Set<Beneficiario> beneficiarios) {
        this.beneficiarios = beneficiarios;
    }

    public Intolerancia beneficiarios(Set<Beneficiario> beneficiarios) {
        this.setBeneficiarios(beneficiarios);
        return this;
    }

    public Intolerancia addBeneficiario(Beneficiario beneficiario) {
        this.beneficiarios.add(beneficiario);
        beneficiario.getIntolerancias().add(this);
        return this;
    }

    public Intolerancia removeBeneficiario(Beneficiario beneficiario) {
        this.beneficiarios.remove(beneficiario);
        beneficiario.getIntolerancias().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Intolerancia)) {
            return false;
        }
        return id != null && id.equals(((Intolerancia) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Intolerancia{" +
            "id=" + getId() +
            ", nombreIntolerancia='" + getNombreIntolerancia() + "'" +
            "}";
    }
}
