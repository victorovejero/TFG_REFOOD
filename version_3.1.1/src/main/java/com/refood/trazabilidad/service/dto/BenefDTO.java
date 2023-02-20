package com.refood.trazabilidad.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.refood.trazabilidad.domain.Benef} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BenefDTO implements Serializable {

    private Long id;

    @NotNull
    private String idBeneficiario;

    @NotNull
    private String nombreRepresentante;

    @NotNull
    private String primerApellidoRepresentante;

    private String segundoApellidoRepresentante;

    @NotNull
    private Integer numeroPersonas;

    @NotNull
    private String email;

    @NotNull
    private String telefono;

    @NotNull
    private String telefonoSecundario;

    @NotNull
    private String direccion;

    @NotNull
    private String codigoPostal;

    @NotNull
    private LocalDate fechaAlta;

    private LocalDate fechaBaja;

    @NotNull
    private Integer numeroNinios;

    private String idDual;

    @NotNull
    private Boolean activo;

    private Set<IntolDTO> intols = new HashSet<>();

    private NucleoDTO nucleo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(String idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public String getNombreRepresentante() {
        return nombreRepresentante;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    public String getPrimerApellidoRepresentante() {
        return primerApellidoRepresentante;
    }

    public void setPrimerApellidoRepresentante(String primerApellidoRepresentante) {
        this.primerApellidoRepresentante = primerApellidoRepresentante;
    }

    public String getSegundoApellidoRepresentante() {
        return segundoApellidoRepresentante;
    }

    public void setSegundoApellidoRepresentante(String segundoApellidoRepresentante) {
        this.segundoApellidoRepresentante = segundoApellidoRepresentante;
    }

    public Integer getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(Integer numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefonoSecundario() {
        return telefonoSecundario;
    }

    public void setTelefonoSecundario(String telefonoSecundario) {
        this.telefonoSecundario = telefonoSecundario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Integer getNumeroNinios() {
        return numeroNinios;
    }

    public void setNumeroNinios(Integer numeroNinios) {
        this.numeroNinios = numeroNinios;
    }

    public String getIdDual() {
        return idDual;
    }

    public void setIdDual(String idDual) {
        this.idDual = idDual;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<IntolDTO> getIntols() {
        return intols;
    }

    public void setIntols(Set<IntolDTO> intols) {
        this.intols = intols;
    }

    public NucleoDTO getNucleo() {
        return nucleo;
    }

    public void setNucleo(NucleoDTO nucleo) {
        this.nucleo = nucleo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BenefDTO)) {
            return false;
        }

        BenefDTO benefDTO = (BenefDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, benefDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BenefDTO{" +
            "id=" + getId() +
            ", idBeneficiario='" + getIdBeneficiario() + "'" +
            ", nombreRepresentante='" + getNombreRepresentante() + "'" +
            ", primerApellidoRepresentante='" + getPrimerApellidoRepresentante() + "'" +
            ", segundoApellidoRepresentante='" + getSegundoApellidoRepresentante() + "'" +
            ", numeroPersonas=" + getNumeroPersonas() +
            ", email='" + getEmail() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", telefonoSecundario='" + getTelefonoSecundario() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", fechaBaja='" + getFechaBaja() + "'" +
            ", numeroNinios=" + getNumeroNinios() +
            ", idDual='" + getIdDual() + "'" +
            ", activo='" + getActivo() + "'" +
            ", intols=" + getIntols() +
            ", nucleo=" + getNucleo() +
            "}";
    }
}
