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
 * A PersonaBeneficiaria.
 */
@Entity
@Table(name = "persona_beneficiaria")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonaBeneficiaria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "primer_apellido", nullable = false)
    private String primerApellido;

    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @NotNull
    @Column(name = "fecha_nacimiento", nullable = false)
    private String fechaNacimiento;

    @NotNull
    @Column(name = "sexo", nullable = false)
    private String sexo;

    @NotNull
    @Column(name = "parentesco", nullable = false)
    private String parentesco;

    @NotNull
    @Column(name = "situacion_profesional", nullable = false)
    private String situacionProfesional;

    @ManyToMany
    @JoinTable(
        name = "rel_persona_beneficiaria__intolerancia",
        joinColumns = @JoinColumn(name = "persona_beneficiaria_id"),
        inverseJoinColumns = @JoinColumn(name = "intolerancia_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "beneficiarios", "personaBeneficiarias", "tipoDeAlimentos" }, allowSetters = true)
    private Set<Intolerancia> intolerancias = new HashSet<>();

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

    public PersonaBeneficiaria id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public PersonaBeneficiaria nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return this.primerApellido;
    }

    public PersonaBeneficiaria primerApellido(String primerApellido) {
        this.setPrimerApellido(primerApellido);
        return this;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return this.segundoApellido;
    }

    public PersonaBeneficiaria segundoApellido(String segundoApellido) {
        this.setSegundoApellido(segundoApellido);
        return this;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public PersonaBeneficiaria fechaNacimiento(String fechaNacimiento) {
        this.setFechaNacimiento(fechaNacimiento);
        return this;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return this.sexo;
    }

    public PersonaBeneficiaria sexo(String sexo) {
        this.setSexo(sexo);
        return this;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getParentesco() {
        return this.parentesco;
    }

    public PersonaBeneficiaria parentesco(String parentesco) {
        this.setParentesco(parentesco);
        return this;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getSituacionProfesional() {
        return this.situacionProfesional;
    }

    public PersonaBeneficiaria situacionProfesional(String situacionProfesional) {
        this.setSituacionProfesional(situacionProfesional);
        return this;
    }

    public void setSituacionProfesional(String situacionProfesional) {
        this.situacionProfesional = situacionProfesional;
    }

    public Set<Intolerancia> getIntolerancias() {
        return this.intolerancias;
    }

    public void setIntolerancias(Set<Intolerancia> intolerancias) {
        this.intolerancias = intolerancias;
    }

    public PersonaBeneficiaria intolerancias(Set<Intolerancia> intolerancias) {
        this.setIntolerancias(intolerancias);
        return this;
    }

    public PersonaBeneficiaria addIntolerancia(Intolerancia intolerancia) {
        this.intolerancias.add(intolerancia);
        intolerancia.getPersonaBeneficiarias().add(this);
        return this;
    }

    public PersonaBeneficiaria removeIntolerancia(Intolerancia intolerancia) {
        this.intolerancias.remove(intolerancia);
        intolerancia.getPersonaBeneficiarias().remove(this);
        return this;
    }

    public Beneficiario getBeneficiario() {
        return this.beneficiario;
    }

    public void setBeneficiario(Beneficiario beneficiario) {
        this.beneficiario = beneficiario;
    }

    public PersonaBeneficiaria beneficiario(Beneficiario beneficiario) {
        this.setBeneficiario(beneficiario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonaBeneficiaria)) {
            return false;
        }
        return id != null && id.equals(((PersonaBeneficiaria) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonaBeneficiaria{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", parentesco='" + getParentesco() + "'" +
            ", situacionProfesional='" + getSituacionProfesional() + "'" +
            "}";
    }
}
