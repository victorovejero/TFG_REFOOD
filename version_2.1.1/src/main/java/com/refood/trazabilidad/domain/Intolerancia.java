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

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany(mappedBy = "intolerancias")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "alimentoDeSalidas", "personaBeneficiarias", "checkouts", "intolerancias", "nucleo" },
        allowSetters = true
    )
    private Set<Beneficiario> beneficiarios = new HashSet<>();

    @ManyToMany(mappedBy = "intolerancias")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "intolerancias", "beneficiario" }, allowSetters = true)
    private Set<PersonaBeneficiaria> personaBeneficiarias = new HashSet<>();

    @ManyToMany(mappedBy = "intolerancias")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "alimentoDeEntradas", "intolerancias" }, allowSetters = true)
    private Set<TipoDeAlimento> tipoDeAlimentos = new HashSet<>();

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

    public String getNombre() {
        return this.nombre;
    }

    public Intolerancia nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Intolerancia descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Beneficiario> getBeneficiarios() {
        return this.beneficiarios;
    }

    public void setBeneficiarios(Set<Beneficiario> beneficiarios) {
        if (this.beneficiarios != null) {
            this.beneficiarios.forEach(i -> i.removeIntolerancia(this));
        }
        if (beneficiarios != null) {
            beneficiarios.forEach(i -> i.addIntolerancia(this));
        }
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

    public Set<PersonaBeneficiaria> getPersonaBeneficiarias() {
        return this.personaBeneficiarias;
    }

    public void setPersonaBeneficiarias(Set<PersonaBeneficiaria> personaBeneficiarias) {
        if (this.personaBeneficiarias != null) {
            this.personaBeneficiarias.forEach(i -> i.removeIntolerancia(this));
        }
        if (personaBeneficiarias != null) {
            personaBeneficiarias.forEach(i -> i.addIntolerancia(this));
        }
        this.personaBeneficiarias = personaBeneficiarias;
    }

    public Intolerancia personaBeneficiarias(Set<PersonaBeneficiaria> personaBeneficiarias) {
        this.setPersonaBeneficiarias(personaBeneficiarias);
        return this;
    }

    public Intolerancia addPersonaBeneficiaria(PersonaBeneficiaria personaBeneficiaria) {
        this.personaBeneficiarias.add(personaBeneficiaria);
        personaBeneficiaria.getIntolerancias().add(this);
        return this;
    }

    public Intolerancia removePersonaBeneficiaria(PersonaBeneficiaria personaBeneficiaria) {
        this.personaBeneficiarias.remove(personaBeneficiaria);
        personaBeneficiaria.getIntolerancias().remove(this);
        return this;
    }

    public Set<TipoDeAlimento> getTipoDeAlimentos() {
        return this.tipoDeAlimentos;
    }

    public void setTipoDeAlimentos(Set<TipoDeAlimento> tipoDeAlimentos) {
        if (this.tipoDeAlimentos != null) {
            this.tipoDeAlimentos.forEach(i -> i.removeIntolerancia(this));
        }
        if (tipoDeAlimentos != null) {
            tipoDeAlimentos.forEach(i -> i.addIntolerancia(this));
        }
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
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
