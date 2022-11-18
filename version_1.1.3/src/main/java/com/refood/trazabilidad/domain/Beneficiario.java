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
    @Column(name = "id_beneficiario", nullable = false)
    private String idBeneficiario;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "numero_personas", nullable = false)
    private Integer numeroPersonas;

    @NotNull
    @Column(name = "numero_ninios", nullable = false)
    private Integer numeroNinios;

    @Column(name = "id_dual")
    private String idDual;

    @NotNull
    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "beneficiario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tupper", "beneficiario", "alimentoDeEntrada" }, allowSetters = true)
    private Set<AlimentoDeSalida> alimentoDeSalidas = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_beneficiario__intolerancia",
        joinColumns = @JoinColumn(name = "beneficiario_id"),
        inverseJoinColumns = @JoinColumn(name = "intolerancia_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "beneficiarios", "tipoDeAlimentos" }, allowSetters = true)
    private Set<Intolerancia> intolerancias = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "donantes", "beneficiarios", "voluntarios", "socios", "registros" }, allowSetters = true)
    private Nucleo nucleo;

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

    public String getIdBeneficiario() {
        return this.idBeneficiario;
    }

    public Beneficiario idBeneficiario(String idBeneficiario) {
        this.setIdBeneficiario(idBeneficiario);
        return this;
    }

    public void setIdBeneficiario(String idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Beneficiario nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumeroPersonas() {
        return this.numeroPersonas;
    }

    public Beneficiario numeroPersonas(Integer numeroPersonas) {
        this.setNumeroPersonas(numeroPersonas);
        return this;
    }

    public void setNumeroPersonas(Integer numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    public Integer getNumeroNinios() {
        return this.numeroNinios;
    }

    public Beneficiario numeroNinios(Integer numeroNinios) {
        this.setNumeroNinios(numeroNinios);
        return this;
    }

    public void setNumeroNinios(Integer numeroNinios) {
        this.numeroNinios = numeroNinios;
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

    public Boolean getActivo() {
        return this.activo;
    }

    public Beneficiario activo(Boolean activo) {
        this.setActivo(activo);
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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

    public Set<Intolerancia> getIntolerancias() {
        return this.intolerancias;
    }

    public void setIntolerancias(Set<Intolerancia> intolerancias) {
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
            ", idBeneficiario='" + getIdBeneficiario() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", numeroPersonas=" + getNumeroPersonas() +
            ", numeroNinios=" + getNumeroNinios() +
            ", idDual='" + getIdDual() + "'" +
            ", activo='" + getActivo() + "'" +
            "}";
    }
}
