package com.refood.trazabilidad.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.refood.trazabilidad.domain.Donante} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DonanteDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombreDonante;

    @NotNull
    private String tipoDonante;

    @NotNull
    private Integer ruta;

    @NotNull
    private String direccionDonante;

    @NotNull
    private String telefonoDonante;

    @NotNull
    private String emailDonante;

    @NotNull
    private String responsableDonante;

    @NotNull
    private LocalDate fechaAlta;

    private LocalDate fechaBaja;

    private String comentarios;

    private NucleoDTO nucleo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreDonante() {
        return nombreDonante;
    }

    public void setNombreDonante(String nombreDonante) {
        this.nombreDonante = nombreDonante;
    }

    public String getTipoDonante() {
        return tipoDonante;
    }

    public void setTipoDonante(String tipoDonante) {
        this.tipoDonante = tipoDonante;
    }

    public Integer getRuta() {
        return ruta;
    }

    public void setRuta(Integer ruta) {
        this.ruta = ruta;
    }

    public String getDireccionDonante() {
        return direccionDonante;
    }

    public void setDireccionDonante(String direccionDonante) {
        this.direccionDonante = direccionDonante;
    }

    public String getTelefonoDonante() {
        return telefonoDonante;
    }

    public void setTelefonoDonante(String telefonoDonante) {
        this.telefonoDonante = telefonoDonante;
    }

    public String getEmailDonante() {
        return emailDonante;
    }

    public void setEmailDonante(String emailDonante) {
        this.emailDonante = emailDonante;
    }

    public String getResponsableDonante() {
        return responsableDonante;
    }

    public void setResponsableDonante(String responsableDonante) {
        this.responsableDonante = responsableDonante;
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

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
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
        if (!(o instanceof DonanteDTO)) {
            return false;
        }

        DonanteDTO donanteDTO = (DonanteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, donanteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonanteDTO{" +
            "id=" + getId() +
            ", nombreDonante='" + getNombreDonante() + "'" +
            ", tipoDonante='" + getTipoDonante() + "'" +
            ", ruta=" + getRuta() +
            ", direccionDonante='" + getDireccionDonante() + "'" +
            ", telefonoDonante='" + getTelefonoDonante() + "'" +
            ", emailDonante='" + getEmailDonante() + "'" +
            ", responsableDonante='" + getResponsableDonante() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", fechaBaja='" + getFechaBaja() + "'" +
            ", comentarios='" + getComentarios() + "'" +
            ", nucleo=" + getNucleo() +
            "}";
    }
}
