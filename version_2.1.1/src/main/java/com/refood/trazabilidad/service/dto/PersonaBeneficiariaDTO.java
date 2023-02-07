package com.refood.trazabilidad.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.refood.trazabilidad.domain.PersonaBeneficiaria} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PersonaBeneficiariaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String primerApellido;

    private String segundoApellido;

    @NotNull
    private String fechaNacimiento;

    @NotNull
    private String sexo;

    @NotNull
    private String parentesco;

    @NotNull
    private String situacionProfesional;

    private Set<IntoleranciaDTO> intolerancias = new HashSet<>();

    private BeneficiarioDTO beneficiario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getSituacionProfesional() {
        return situacionProfesional;
    }

    public void setSituacionProfesional(String situacionProfesional) {
        this.situacionProfesional = situacionProfesional;
    }

    public Set<IntoleranciaDTO> getIntolerancias() {
        return intolerancias;
    }

    public void setIntolerancias(Set<IntoleranciaDTO> intolerancias) {
        this.intolerancias = intolerancias;
    }

    public BeneficiarioDTO getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(BeneficiarioDTO beneficiario) {
        this.beneficiario = beneficiario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonaBeneficiariaDTO)) {
            return false;
        }

        PersonaBeneficiariaDTO personaBeneficiariaDTO = (PersonaBeneficiariaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personaBeneficiariaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonaBeneficiariaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", parentesco='" + getParentesco() + "'" +
            ", situacionProfesional='" + getSituacionProfesional() + "'" +
            ", intolerancias=" + getIntolerancias() +
            ", beneficiario=" + getBeneficiario() +
            "}";
    }
}
