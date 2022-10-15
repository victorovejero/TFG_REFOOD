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
 * A Beneficiario.
 */
@Entity
@Table(name = "beneficiario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Beneficiario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre_beneficiario", nullable = false)
    private String nombreBeneficiario;

    @NotNull
    @Column(name = "num_personas", nullable = false)
    private Integer numPersonas;

    @NotNull
    @Column(name = "num_ninios", nullable = false)
    private Integer numNinios;

    @Column(name = "id_dual")
    private String idDual;

    @OneToMany(mappedBy = "beneficiario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tupper", "beneficiario", "alimentoDeEntrada", "tipoDeAlimento" }, allowSetters = true)
    private Set<AlimentoDeSalida> alimentoDeSalidas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "donantes", "beneficiarios", "voluntarios", "socios", "registros" }, allowSetters = true)
    private Nucleo nucleo;

    @ManyToMany(mappedBy = "beneficiarios")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tipoDeAlimentos", "beneficiarios" }, allowSetters = true)
    private Set<Intolerancia> intolerancias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Beneficiario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreBeneficiario() {
        return this.nombreBeneficiario;
    }

    public Beneficiario nombreBeneficiario(String nombreBeneficiario) {
        this.setNombreBeneficiario(nombreBeneficiario);
        return this;
    }

    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    public Integer getNumPersonas() {
        return this.numPersonas;
    }

    public Beneficiario numPersonas(Integer numPersonas) {
        this.setNumPersonas(numPersonas);
        return this;
    }

    public void setNumPersonas(Integer numPersonas) {
        this.numPersonas = numPersonas;
    }

    public Integer getNumNinios() {
        return this.numNinios;
    }

    public Beneficiario numNinios(Integer numNinios) {
        this.setNumNinios(numNinios);
        return this;
    }

    public void setNumNinios(Integer numNinios) {
        this.numNinios = numNinios;
    }

    public String getIdDual() {
        return this.idDual;
    }

    public Beneficiario idDual(String idDual) {
        this.setIdDual(idDual);
        return this;
    }

    public void setIdDual(String idDual) {
        this.idDual = idDual;
    }

    public Set<AlimentoDeSalida> getAlimentoDeSalidas() {
        return this.alimentoDeSalidas;
    }

    public void setAlimentoDeSalidas(Set<AlimentoDeSalida> alimentoDeSalidas) {
        if (this.alimentoDeSalidas != null) {
            this.alimentoDeSalidas.forEach(i -> i.setBeneficiario(null));
        }
        if (alimentoDeSalidas != null) {
            alimentoDeSalidas.forEach(i -> i.setBeneficiario(this));
        }
        this.alimentoDeSalidas = alimentoDeSalidas;
    }

    public Beneficiario alimentoDeSalidas(Set<AlimentoDeSalida> alimentoDeSalidas) {
        this.setAlimentoDeSalidas(alimentoDeSalidas);
        return this;
    }

    public Beneficiario addAlimentoDeSalida(AlimentoDeSalida alimentoDeSalida) {
        this.alimentoDeSalidas.add(alimentoDeSalida);
        alimentoDeSalida.setBeneficiario(this);
        return this;
    }

    public Beneficiario removeAlimentoDeSalida(AlimentoDeSalida alimentoDeSalida) {
        this.alimentoDeSalidas.remove(alimentoDeSalida);
        alimentoDeSalida.setBeneficiario(null);
        return this;
    }

    public Nucleo getNucleo() {
        return this.nucleo;
    }

    public void setNucleo(Nucleo nucleo) {
        this.nucleo = nucleo;
    }

    public Beneficiario nucleo(Nucleo nucleo) {
        this.setNucleo(nucleo);
        return this;
    }

    public Set<Intolerancia> getIntolerancias() {
        return this.intolerancias;
    }

    public void setIntolerancias(Set<Intolerancia> intolerancias) {
        if (this.intolerancias != null) {
            this.intolerancias.forEach(i -> i.removeBeneficiario(this));
        }
        if (intolerancias != null) {
            intolerancias.forEach(i -> i.addBeneficiario(this));
        }
        this.intolerancias = intolerancias;
    }

    public Beneficiario intolerancias(Set<Intolerancia> intolerancias) {
        this.setIntolerancias(intolerancias);
        return this;
    }

    public Beneficiario addIntolerancia(Intolerancia intolerancia) {
        this.intolerancias.add(intolerancia);
        intolerancia.getBeneficiarios().add(this);
        return this;
    }

    public Beneficiario removeIntolerancia(Intolerancia intolerancia) {
        this.intolerancias.remove(intolerancia);
        intolerancia.getBeneficiarios().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beneficiario)) {
            return false;
        }
        return id != null && id.equals(((Beneficiario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Beneficiario{" +
            "id=" + getId() +
            ", nombreBeneficiario='" + getNombreBeneficiario() + "'" +
            ", numPersonas=" + getNumPersonas() +
            ", numNinios=" + getNumNinios() +
            ", idDual='" + getIdDual() + "'" +
            "}";
    }
}
