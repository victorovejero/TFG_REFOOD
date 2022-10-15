package com.refood.trazabilidad.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.refood.trazabilidad.domain.Socio} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SocioDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombreSocio;

    @NotNull
    private String primerApellidoSocio;

    private String segundoApellidoSocio;

    @NotNull
    private String emailSocio;

    @NotNull
    private String telefonoContactoSocio;

    @NotNull
    private String dniSocio;

    @NotNull
    private LocalDate fechaNacimientoSocio;

    @NotNull
    private String sexoSocio;

    @NotNull
    private LocalDate fechaAltaSocio;

    private LocalDate fechaBajaSocio;

    @NotNull
    private Double contribucionMensual;

    @NotNull
    private String periodoPago;

    private NucleoDTO nucleo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreSocio() {
        return nombreSocio;
    }

    public void setNombreSocio(String nombreSocio) {
        this.nombreSocio = nombreSocio;
    }

    public String getPrimerApellidoSocio() {
        return primerApellidoSocio;
    }

    public void setPrimerApellidoSocio(String primerApellidoSocio) {
        this.primerApellidoSocio = primerApellidoSocio;
    }

    public String getSegundoApellidoSocio() {
        return segundoApellidoSocio;
    }

    public void setSegundoApellidoSocio(String segundoApellidoSocio) {
        this.segundoApellidoSocio = segundoApellidoSocio;
    }

    public String getEmailSocio() {
        return emailSocio;
    }

    public void setEmailSocio(String emailSocio) {
        this.emailSocio = emailSocio;
    }

    public String getTelefonoContactoSocio() {
        return telefonoContactoSocio;
    }

    public void setTelefonoContactoSocio(String telefonoContactoSocio) {
        this.telefonoContactoSocio = telefonoContactoSocio;
    }

    public String getDniSocio() {
        return dniSocio;
    }

    public void setDniSocio(String dniSocio) {
        this.dniSocio = dniSocio;
    }

    public LocalDate getFechaNacimientoSocio() {
        return fechaNacimientoSocio;
    }

    public void setFechaNacimientoSocio(LocalDate fechaNacimientoSocio) {
        this.fechaNacimientoSocio = fechaNacimientoSocio;
    }

    public String getSexoSocio() {
        return sexoSocio;
    }

    public void setSexoSocio(String sexoSocio) {
        this.sexoSocio = sexoSocio;
    }

    public LocalDate getFechaAltaSocio() {
        return fechaAltaSocio;
    }

    public void setFechaAltaSocio(LocalDate fechaAltaSocio) {
        this.fechaAltaSocio = fechaAltaSocio;
    }

    public LocalDate getFechaBajaSocio() {
        return fechaBajaSocio;
    }

    public void setFechaBajaSocio(LocalDate fechaBajaSocio) {
        this.fechaBajaSocio = fechaBajaSocio;
    }

    public Double getContribucionMensual() {
        return contribucionMensual;
    }

    public void setContribucionMensual(Double contribucionMensual) {
        this.contribucionMensual = contribucionMensual;
    }

    public String getPeriodoPago() {
        return periodoPago;
    }

    public void setPeriodoPago(String periodoPago) {
        this.periodoPago = periodoPago;
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
        if (!(o instanceof SocioDTO)) {
            return false;
        }

        SocioDTO socioDTO = (SocioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, socioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocioDTO{" +
            "id=" + getId() +
            ", nombreSocio='" + getNombreSocio() + "'" +
            ", primerApellidoSocio='" + getPrimerApellidoSocio() + "'" +
            ", segundoApellidoSocio='" + getSegundoApellidoSocio() + "'" +
            ", emailSocio='" + getEmailSocio() + "'" +
            ", telefonoContactoSocio='" + getTelefonoContactoSocio() + "'" +
            ", dniSocio='" + getDniSocio() + "'" +
            ", fechaNacimientoSocio='" + getFechaNacimientoSocio() + "'" +
            ", sexoSocio='" + getSexoSocio() + "'" +
            ", fechaAltaSocio='" + getFechaAltaSocio() + "'" +
            ", fechaBajaSocio='" + getFechaBajaSocio() + "'" +
            ", contribucionMensual=" + getContribucionMensual() +
            ", periodoPago='" + getPeriodoPago() + "'" +
            ", nucleo=" + getNucleo() +
            "}";
    }
}
