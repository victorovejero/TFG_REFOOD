package com.refood.trazabilidad.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.refood.trazabilidad.domain.Voluntario} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VoluntarioDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombreVoluntario;

    @NotNull
    private String primerApellido;

    private String segundoApellido;

    @NotNull
    private String emailVoluntario;

    @NotNull
    private String telefonoContactoVoluntario;

    private String dniVoluntario;

    @NotNull
    private LocalDate fechaNacimientoVoluntario;

    @NotNull
    private String sexoVoluntario;

    @NotNull
    private LocalDate fechaAlta;

    private LocalDate fechaBaja;

    @NotNull
    private String tipoVoluntario;

    private String tipoTurno;

    private Boolean responsableDia;

    private String origenVoluntario;

    @NotNull
    private Boolean manipuladorAlimentos;

    @NotNull
    private String codigoPostal;

    private NucleoDTO nucleo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreVoluntario() {
        return nombreVoluntario;
    }

    public void setNombreVoluntario(String nombreVoluntario) {
        this.nombreVoluntario = nombreVoluntario;
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

    public String getEmailVoluntario() {
        return emailVoluntario;
    }

    public void setEmailVoluntario(String emailVoluntario) {
        this.emailVoluntario = emailVoluntario;
    }

    public String getTelefonoContactoVoluntario() {
        return telefonoContactoVoluntario;
    }

    public void setTelefonoContactoVoluntario(String telefonoContactoVoluntario) {
        this.telefonoContactoVoluntario = telefonoContactoVoluntario;
    }

    public String getDniVoluntario() {
        return dniVoluntario;
    }

    public void setDniVoluntario(String dniVoluntario) {
        this.dniVoluntario = dniVoluntario;
    }

    public LocalDate getFechaNacimientoVoluntario() {
        return fechaNacimientoVoluntario;
    }

    public void setFechaNacimientoVoluntario(LocalDate fechaNacimientoVoluntario) {
        this.fechaNacimientoVoluntario = fechaNacimientoVoluntario;
    }

    public String getSexoVoluntario() {
        return sexoVoluntario;
    }

    public void setSexoVoluntario(String sexoVoluntario) {
        this.sexoVoluntario = sexoVoluntario;
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

    public String getTipoVoluntario() {
        return tipoVoluntario;
    }

    public void setTipoVoluntario(String tipoVoluntario) {
        this.tipoVoluntario = tipoVoluntario;
    }

    public String getTipoTurno() {
        return tipoTurno;
    }

    public void setTipoTurno(String tipoTurno) {
        this.tipoTurno = tipoTurno;
    }

    public Boolean getResponsableDia() {
        return responsableDia;
    }

    public void setResponsableDia(Boolean responsableDia) {
        this.responsableDia = responsableDia;
    }

    public String getOrigenVoluntario() {
        return origenVoluntario;
    }

    public void setOrigenVoluntario(String origenVoluntario) {
        this.origenVoluntario = origenVoluntario;
    }

    public Boolean getManipuladorAlimentos() {
        return manipuladorAlimentos;
    }

    public void setManipuladorAlimentos(Boolean manipuladorAlimentos) {
        this.manipuladorAlimentos = manipuladorAlimentos;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
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
        if (!(o instanceof VoluntarioDTO)) {
            return false;
        }

        VoluntarioDTO voluntarioDTO = (VoluntarioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, voluntarioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoluntarioDTO{" +
            "id=" + getId() +
            ", nombreVoluntario='" + getNombreVoluntario() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", emailVoluntario='" + getEmailVoluntario() + "'" +
            ", telefonoContactoVoluntario='" + getTelefonoContactoVoluntario() + "'" +
            ", dniVoluntario='" + getDniVoluntario() + "'" +
            ", fechaNacimientoVoluntario='" + getFechaNacimientoVoluntario() + "'" +
            ", sexoVoluntario='" + getSexoVoluntario() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", fechaBaja='" + getFechaBaja() + "'" +
            ", tipoVoluntario='" + getTipoVoluntario() + "'" +
            ", tipoTurno='" + getTipoTurno() + "'" +
            ", responsableDia='" + getResponsableDia() + "'" +
            ", origenVoluntario='" + getOrigenVoluntario() + "'" +
            ", manipuladorAlimentos='" + getManipuladorAlimentos() + "'" +
            ", codigoPostal='" + getCodigoPostal() + "'" +
            ", nucleo=" + getNucleo() +
            "}";
    }
}
